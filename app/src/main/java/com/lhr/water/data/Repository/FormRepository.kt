package com.lhr.water.data.Repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lhr.water.R
import com.lhr.water.room.SqlDatabase
import com.lhr.water.util.jsonStringToJson
import org.json.JSONObject

class FormRepository(context: Context) {

    var formRecordList: MutableLiveData<ArrayList<JSONObject>> =
        MutableLiveData<ArrayList<JSONObject>>()

    // 篩選後的表單
    var formFilterRecordList: MutableLiveData<ArrayList<JSONObject>> =
        MutableLiveData<ArrayList<JSONObject>>()
    // 篩選表單類別FormClass的List
    var filterList = MutableLiveData<ArrayList<String>>()
    // 篩選表單代號ReportId的String
    var searchReportId = MutableLiveData<String>()

    companion object {
        private var instance: FormRepository? = null
        fun getInstance(context: Context): FormRepository {
            if (instance == null) {
                instance = FormRepository(context)
            }
            return instance!!
        }
    }

    init {
        filterList.value = ArrayList(context.resources.getStringArray(R.array.form_array).toList())
        val loadFormList: List<String> = SqlDatabase.getInstance().getDeliveryDao().getAll()
        val formJsonList = ArrayList<JSONObject>()
        for (formData in loadFormList) {
            formJsonList.add(jsonStringToJson(formData))
        }
        formRecordList.value = loadRecord()

    }

    /**
     * 抓取表單全部記錄
     */
    fun loadRecord(): ArrayList<JSONObject> {
        val loadFormList: List<String> = SqlDatabase.getInstance().getDeliveryDao().getAll()
        val formJsonList = ArrayList<JSONObject>()
        for (formData in loadFormList) {
            formJsonList.add(jsonStringToJson(formData))
        }
        formRecordList.value = formJsonList
        formFilterRecordList.value = formJsonList
        formFilterRecordList.value = filterRecord()
        return formJsonList
    }

    /**
     * 篩選表單內容
     */
    fun filterRecord(): ArrayList<JSONObject>? {
//        formFilterRecordList.postValue(
           return formRecordList.value?.filter { jsonObject ->
                // 根據 "FormClass" 判斷是否在 filterList 中
                val formClass = jsonObject.optString("FormClass")
                val formClassFilterCondition = filterList.value?.contains(formClass)

                val reportId = jsonObject.optString("ReportId")

                // 如果EditText中的文本不為空，則判斷 "ReportId" 是否包含該文本
                val editTextFilterCondition = if (searchReportId.value?.isNotEmpty() == true) {
                    reportId.contains(searchReportId.value!!, ignoreCase = true)
                } else {
                    true // 如果EditText為空，不添加 ReportId 的篩選條件
                }
                formClassFilterCondition!! && editTextFilterCondition
            }?.toMutableList()!! as ArrayList<JSONObject>?
//        )
    }
}