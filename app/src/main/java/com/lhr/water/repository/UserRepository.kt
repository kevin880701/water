package com.lhr.water.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.room.FormEntity

class UserRepository private constructor(private val context: Context) {

//    var userInfo = MutableLiveData<UserInfo>(UserInfo(
//        deptAno = "",
//        userId = ""
//    ))
    var userInfo = MutableLiveData<UserInfo>(UserInfo(
        deptAno = "",
        userId = ""
    ))
//    var userInfo = UserInfo(
//        deptAno = "",
//        userId = ""
//    )

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