package com.lhr.water.network

import com.lhr.water.network.data.UpdateData
import com.lhr.water.network.data.response.BaseResponse
import com.lhr.water.network.data.response.UserInfoData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url


interface ApiService {
    @GET
    fun fetchJsonFrom(@Url url: String): Call<UpdateData>

//    @POST("PDA/getUserInfo")
//    fun getUserInfo(@Query("email") email: String?): Single<Response<String?>?>?

    @POST("PDA/getUserInfo")
    fun getUserInfo(): Observable<BaseResponse<UserInfoData>>

//    @POST("PDA/getWhseStrg")
//    fun getWhseStrg(@Body userInfo: UserInfo): Observable<ApiResponse<WhseStrgResponse>>
//
//    @POST("PDA/updateFromPDA")
//    fun updateFromPDA(@Body data: UpdateFromPdaRequest): Observable<ApiResponse<WhseStrgResponse>>
}