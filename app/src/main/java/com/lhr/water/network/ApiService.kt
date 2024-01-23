package com.lhr.water.network

import com.lhr.water.network.data.UpdateData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import java.util.Objects

interface ApiService {
    @GET
    fun fetchJsonFrom(@Url url: String): Call<UpdateData>

//    @POST
//    fun postJsonTo(
//        @Url url: String,
//        @Body jsonBody: ArrayList<UpdateRequestBodyDto>
//    ): Call<ResponseBodyType>
}