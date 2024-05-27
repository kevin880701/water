package com.lhr.water.ui.cover

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.network.ApiManager
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.ui.base.APP
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class CoverViewModel(
    var context: Context,
    var formRepository: FormRepository,
    var regionRepository: RegionRepository,
    var userRepository: UserRepository
) : AndroidViewModel(context.applicationContext as APP) {

    fun getUserInfo(): Observable<UserInfo> {
        return ApiManager().getUserInfo()
            .subscribeOn(Schedulers.io())
            .map { response ->
                userRepository.userInfo = response.data.userInfo
                response.data.userInfo
            }
//            .subscribe({ response ->
//                userRepository.userInfo = response
////                var gson = Gson()
////                userRepository.userInfo = gson.fromJson(response.data.userInfo, UserInfo::class.java)
//            }, { error ->
//                println("請求失敗：${error.message}")
//            })
    }

    fun getDataList() {
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
}