package com.lhr.water.repository

import android.content.Context
import com.lhr.water.network.data.response.UserInfo

class UserRepository private constructor(private val context: Context) {

    var userInfo = UserInfo(
        deptAno = "0D60",
        userId = "123"
    )

    companion object {
        private var instance: UserRepository? = null
        fun getInstance(context: Context): UserRepository {
            if (instance == null) {
                instance = UserRepository(context)
            }
            return instance!!
        }
    }

    init {
    }
}