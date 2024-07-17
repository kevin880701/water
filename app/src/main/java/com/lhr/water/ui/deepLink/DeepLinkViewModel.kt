package com.lhr.water.ui.deepLink

import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import com.lhr.water.network.ApiManager
import com.lhr.water.network.data.request.DataList
import com.lhr.water.network.data.request.UpdateDataRequest
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.room.SqlDatabase
import com.lhr.water.ui.base.APP
import com.lhr.water.util.SharedPreferencesHelper
import com.lhr.water.util.showToast
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException

class DeepLinkViewModel(var context: Context, var formRepository: FormRepository,
    var regionRepository: RegionRepository,
    var userRepository: UserRepository
) :
    AndroidViewModel(context.applicationContext as APP) {
    var sqlDatabase = SqlDatabase.getInstance()

    fun updatePdaData(userInfo: UserInfo) {
        ApiManager().getDataList(userInfo)
            .subscribeOn(Schedulers.io())
            .map { response ->

                val tempList = response.data.dataList.storageRecordList
                tempList.forEach { storageRecord ->
                    storageRecord.isUpdate = true
                }

                response.data.dataList.storageRecordList.forEach { it.isUpdate = true }
                formRepository.updateSqlData(
                    response.data.dataList.checkoutFormList,
                    tempList,
                    response.data.dataList.deliveryFormList,
                    response.data.dataList.transferFormList,
                    response.data.dataList.receiveFormList,
                    response.data.dataList.returnFormList,
                    response.data.dataList.inventoryFormList
                )
                regionRepository.updateSqlData(response.data.dataList.storageList)
            }
            .subscribe({ response ->
                println("請求成功")
            }, { error ->
                showToast(context,"更新資料失敗！")
                println("請求失敗：${error.message}")
            })
    }

    fun uploadPdaData(userInfo: UserInfo) {
        try {
            if(userInfo.deptAno == ""){
                getUserInfo().subscribe({ getUserInfoResponse ->
                    println("getUserInfoResponse請求成功")
                    updatePdaData(getUserInfoResponse)
                }, { error ->
                    println("請求失敗：${error.message}")
                })
            }else{
                val gson = Gson()

                val inventoryEntities = sqlDatabase.getInventoryDao().getAllNotUpdated()
                val storageRecordEntities = sqlDatabase.getStorageRecordDao().getAllNotUpdated()
                val formEntities = sqlDatabase.getFormDao().getAllNotUpdated()

                // 把formEntities轉成動應Class的List
                val deliveryFormList = ArrayList<DeliveryForm>()
                val receiveFormList = ArrayList<ReceiveForm>()
                val transferFormList = ArrayList<TransferForm>()
                val returnFormList = ArrayList<ReturnForm>()

                for (formEntity in formEntities) {
                    val formContent = formEntity.formContent
                    val reportTitle = formEntity.reportTitle

                    when (reportTitle) {
                        "交貨通知單" -> {
                            val deliveryForm = gson.fromJson(formContent, DeliveryForm::class.java)
                            deliveryFormList.add(deliveryForm)
                        }

                        "材料領料單" -> {
                            val receiveForm = gson.fromJson(formContent, ReceiveForm::class.java)
                            receiveFormList.add(receiveForm)
                        }

                        "材料調撥單" -> {
                            val transferForm = gson.fromJson(formContent, TransferForm::class.java)
                            transferFormList.add(transferForm)
                        }

                        "材料退料單" -> {
                            val returnForm = gson.fromJson(formContent, ReturnForm::class.java)
                            returnFormList.add(returnForm)
                        }

                        else -> {
                        }
                    }
                }

                val updateDataRequest = UpdateDataRequest(
                    dataList = DataList(
                        deliveryList = deliveryFormList,
                        transferList = transferFormList,
                        receiveList = receiveFormList,
                        returnListList = returnFormList,
                        inventoryEntities = inventoryEntities,
                        storageRecordEntities = storageRecordEntities
                    ),
                    userInfo = userRepository.userInfo.value!!
                )

                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
                println("userRepository.userInfo.deptAno!!：${userInfo.deptAno}")
                println("userRepository.userInfo.userId!!：${userInfo.userId}")
                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")

                ApiManager().updateFromPDA(updateDataRequest)
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response ->
                        println(response.toString())
                        getUserInfo().subscribe({ getUserInfoResponse ->
                            println("updateFromPDA請求成功")

                            // 更新 Form 表中的 isUpdate 為 true
                            formEntities.forEach { formEntity ->
                                formEntity.isUpdate = true
                                sqlDatabase.getFormDao().update(formEntity)
                            }

                            // 更新 StorageRecord 表中的 isUpdate 為 true
                            storageRecordEntities.forEach { storageRecordEntity ->
                                storageRecordEntity.isUpdate = true
                                sqlDatabase.getStorageRecordDao().update(storageRecordEntity)
                            }

                            // 更新 Inventory 表中的 isUpdate 為 true
                            inventoryEntities.forEach { inventoryEntity ->
                                inventoryEntity.isUpdate = true
                                sqlDatabase.getInventoryDao().update(inventoryEntity)
                            }

                            updatePdaData(getUserInfoResponse)
                        }, { error ->
                            println("請求失敗：${error.message}")
                        })
                    }, { error ->
                        showToast(context,"更新資料失敗！")
                        println("sendData Failed：${error.message}")
                    })
            }
        } catch (e: IOException) {
            Log.e("MainActivity", "Error writing JSONObject to file", e)
        }
    }

    fun getUserInfo(): Observable<UserInfo> {
        return ApiManager().getUserInfo()
            .subscribeOn(Schedulers.io())
            .map { response ->
                userRepository.userInfo.postValue(response.data.userInfo)

                SharedPreferencesHelper.saveUserInfo(context, response.data.userInfo)
                response.data.userInfo
            }
    }

    // 判斷儲櫃紀錄StorageRecord、表單FormEntity、盤點表單InventoryEntity是否已經備份
    fun checkIsUpdate(): Boolean {
        val formDao = sqlDatabase.getFormDao()
        val storageRecordDao = sqlDatabase.getStorageRecordDao()
        val inventoryDao = sqlDatabase.getInventoryDao()

        val hasUnUpdatedRecord = listOf(
            formDao.getAll().isNotEmpty() && formDao.getAll().any { !it.isUpdate },
            storageRecordDao.getAll().isNotEmpty() && storageRecordDao.getAll().any { !it.isUpdate },
            inventoryDao.getAll().isNotEmpty() && inventoryDao.getAll().any { !it.isUpdate }
        )

        return !hasUnUpdatedRecord.contains(true)
    }
}