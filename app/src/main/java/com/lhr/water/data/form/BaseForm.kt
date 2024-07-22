package com.lhr.water.data.form

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

open class BaseForm(
){
    @Transient
    open val formId: Int = 0

    @Transient
    open val formNumber: String = ""

    @Transient
    open val dealStatus: String = ""

    @Transient
    open val reportId: String = ""

    @Transient
    open val reportTitle: String = ""

    @Transient
    open val date: String = ""

    @Transient
    open val itemDetails: List<BaseItem> = ArrayList<BaseItem>()

    @Transient
    open val updatedAt: String = ""

    @Transient
    open val isCreateRNumber: String = ""
    fun jsonConvertMap(): Map<String, Any?> {
        val gson = Gson()
        val json = gson.toJson(this) // 将对象转换为 JSON 字符串
        val type: Type = object : TypeToken<Map<String, Any?>>() {}.type
        return gson.fromJson(json, type) // 将 JSON 字符串转换为 Map
    }


    // 判斷是否是進貨
    open fun isInput(): Boolean {
        return true
    }
}