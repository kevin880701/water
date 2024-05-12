package com.lhr.water.network

import com.lhr.water.network.data.response.BaseResponse
import com.lhr.water.network.data.response.UpdateData
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.network.data.response.UserInfoData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager() {
    // 创建 OkHttpClient 实例，设置一些网络请求的属性
    val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间为 10 秒
        .readTimeout(10, TimeUnit.SECONDS) // 读取超时时间为 10 秒
        .writeTimeout(10, TimeUnit.SECONDS) // 写入超时时间为 10 秒
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.31.113:8081/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // help turns Call to Observable
        .build()

    // 创建 ApiService 实例
    val apiService = retrofit.create(ApiService::class.java)

    fun getUserInfo(): Observable<BaseResponse<UserInfoData>> {
        return apiService.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDataList(userInfo: UserInfo): Observable<BaseResponse<UpdateData>> {
        return apiService.getDataList(userInfo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}