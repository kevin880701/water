package com.lhr.water.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.lhr.water.data.RegionInformation
import com.lhr.water.data.UserData
import com.lhr.water.room.MapEntity
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.manager.toMapEntities
import org.json.JSONArray
import java.io.InputStreamReader
import java.nio.charset.Charset

class UserRepository private constructor(private val context: Context) {

    var userData = UserData(
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