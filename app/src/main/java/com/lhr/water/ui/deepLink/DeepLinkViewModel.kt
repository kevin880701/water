package com.lhr.water.ui.deepLink

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.lhr.water.data.form.DeliveryForm
import com.lhr.water.data.form.ReceiveForm
import com.lhr.water.data.form.ReturnForm
import com.lhr.water.data.form.TransferForm
import com.lhr.water.network.ApiManager
import com.lhr.water.network.Execute
import com.lhr.water.network.data.UpdateData
import com.lhr.water.network.data.request.DataList
import com.lhr.water.network.data.request.UpdateDataRequest
import com.lhr.water.network.data.response.UpdateDataResponse
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.room.FormEntity.Companion.convertFormToFormEntities
import com.lhr.water.room.SqlDatabase
import com.lhr.water.ui.base.APP
import com.lhr.water.util.manager.checkJson
import com.lhr.water.util.manager.jsonAddInformation
import com.lhr.water.util.manager.jsonStringToJsonArray
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DeepLinkViewModel(
    context: Context, var formRepository: FormRepository,
    var regionRepository: RegionRepository,
    var userRepository: UserRepository
) :
    AndroidViewModel(context.applicationContext as APP) {
    var sqlDatabase = SqlDatabase.getInstance()

    fun autoDownload() {
        updatePda()
    }

    fun updatePda() {
        ApiManager().getDataList(userRepository.userInfo)
            .subscribeOn(Schedulers.io())
            .map { response ->
                formRepository.updateSqlData(
                    response.data.dataList.checkoutFormList,
                    response.data.dataList.storageRecordList,
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
                println("請求失敗：${error.message}")
            })
    }

    fun autoUpload() {
        try {
            val gson = Gson()

            val inventoryEntities = sqlDatabase.getInventoryDao().getAll()
            val storageRecordEntities = sqlDatabase.getStorageRecordDao().getAll()
            val formEntities = sqlDatabase.getFormDao().getAll()

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
                userInfo = userRepository.userInfo
            )

            ApiManager().updateFromPDA(updateDataRequest)
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    println(response.toString())
                    getUserInfo().subscribe({ response ->
                        println("請求成功")

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

                        updatePda()
                    }, { error ->
                        println("請求失敗：${error.message}")
                    })
                }, { error ->
                    println("sendData Failed：${error.message}")
                })
        } catch (e: IOException) {
            Log.e("MainActivity", "Error writing JSONObject to file", e)
        }
    }

    fun getUserInfo(): Observable<UserInfo> {
        return ApiManager().getUserInfo()
            .subscribeOn(Schedulers.io())
            .map { response ->
                userRepository.userInfo = response.data.userInfo
                response.data.userInfo
            }
    }
}