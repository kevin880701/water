package com.lhr.water.data.Repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.lhr.water.data.DeliveryData
import com.lhr.water.room.FormDao
import com.lhr.water.room.SqlDatabase
import com.lhr.water.util.jsonStringToJson
import org.json.JSONObject

class FormRepository(deliveryDao: FormDao) {

    var formRecordList: MutableLiveData<ArrayList<JSONObject>> =
        MutableLiveData<ArrayList<JSONObject>>()
    companion object{
        private var instance: FormRepository?=null
        fun getInstance(deliveryDao: FormDao): FormRepository {
            if (instance ==null) {
                instance = FormRepository(deliveryDao)
            }
            return instance!!
        }
    }

    /**
     * 抓取表單全部記錄
     */
    fun loadRecord(){
        val loadFormList: List<String> = SqlDatabase.getInstance().getDeliveryDao().getAll()
        val formJsonList = ArrayList<JSONObject>()
        for (formData in loadFormList) {
            formJsonList.add(jsonStringToJson(formData))
        }
        formRecordList.postValue(formJsonList)
    }
}