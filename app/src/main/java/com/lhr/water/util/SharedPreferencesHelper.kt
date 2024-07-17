package com.lhr.water.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.network.data.response.UserInfoData

object SharedPreferencesHelper {

    private const val PREFS_NAME = "user_prefs"
    private const val USER_INFO_KEY = "user_info"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserInfo(context: Context, userInfo: UserInfo) {
        val gson = Gson()
        val jsonString = gson.toJson(userInfo)
        val editor = getSharedPreferences(context).edit()
        editor.putString(USER_INFO_KEY, jsonString)
        editor.apply()
    }

    fun getUserInfo(context: Context): UserInfo? {
        val gson = Gson()
        val jsonString = getSharedPreferences(context).getString(USER_INFO_KEY, null)
        return if (jsonString != null) {
            val type = object : TypeToken<UserInfo>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            null
        }
    }

    fun clearUserInfo(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(USER_INFO_KEY)
        editor.apply()
    }
}