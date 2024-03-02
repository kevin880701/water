package com.lhr.water.network

import android.app.Activity
import android.util.Log
import com.google.gson.Gson
import com.lhr.water.network.data.UpdateData
import com.lhr.water.util.API_BASE
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit


object Execute {
    private const val TAG = "Execute"
    private var call: Call? = null
    private fun connect(activity: Activity, request: Request, callback: Callback) {
        var client: OkHttpClient? = null
        try {
            client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        call = client!!.newCall(request)
        call!!.enqueue(callback)
    }

    private fun connect(request: Request, callback: Callback) {
        var client: OkHttpClient? = null
        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        call = client.newCall(request)
        call!!.enqueue(callback)
    }

    private fun post(activity: Activity, api: String, body: RequestBody, callback: Callback) {
        val request: Request = Request.Builder()
            .url(API_BASE + api)
            .post(body)
            .addHeader("content-Type", "application/json")
            .build()
        if (!api.contains("UploadMemberFile")) {
            Log.e(
                TAG, """
     post : $request
     body : ${bodyToString(request)}
     """.trimIndent()
            )
        }
        if ((API_BASE + api).contains("https")) {
            connect(activity, request, callback)
        } else {
            connect(request, callback)
        }
    }

    private operator fun get(activity: Activity, api: String, callback: Callback) {
        val request: Request = Request.Builder()
            .url(API_BASE + api)
            .get()
            .addHeader("content-Type", "application/json")
            .build()
        Timber.e(
            TAG, """
     get : $request
     body : ${bodyToString(request)}
     """.trimIndent()
        )
        if ((API_BASE + api).contains("https")) {
            connect(activity, request, callback)
        } else {
            connect(request, callback)
        }
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body!!.writeTo(buffer)
            var s = buffer.readUtf8()
            if (s.contains("image/jpeg")) {
                s = "is upload image"
            }
            s
        } catch (e: Exception) {
            Log.e(TAG, "bodyToString : $e")
            "did not work"
        }
    }

    fun getNewForm(
        activity: Activity, callback: Callback
    ) {
        val api = "/PDA/ExportList"
        Execute[activity, api, callback]
    }

    fun postRecord(
        activity: Activity, requestBody: RequestBody, callback: Callback
    ) {
        val api = "/PDA/UpdateStatus"
        Execute.post(activity, api, requestBody, callback)
    }
}