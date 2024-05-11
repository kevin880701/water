package com.lhr.water.ui.cover

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.network.ApiManager
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.ui.base.APP
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class CoverViewModel(var context: Context, var regionRepository: RegionRepository, var userRepository: UserRepository): AndroidViewModel(context.applicationContext as APP) {

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
//                println("请求失败：${error.message}")
//            })
    }
}