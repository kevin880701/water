package com.lhr.water.network

import com.lhr.water.network.data.request.UpdateDataRequest
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
    // 創建 OkHttpClient 實例，設定一些網路請求的屬性
    val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS) // 連接超時時間為 10 秒
        .readTimeout(10, TimeUnit.SECONDS) // 讀取超時時間為 10 秒
        .writeTimeout(10, TimeUnit.SECONDS) // 寫入超時時間為 10 秒
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8081/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // help turns Call to Observable
        .build()

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

    fun updateFromPDA(request: UpdateDataRequest): Observable<BaseResponse<UpdateData>> {
        return apiService.updateFromPDA(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}