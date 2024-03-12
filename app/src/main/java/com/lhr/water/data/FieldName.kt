package com.lhr.water.data.form

import kotlin.reflect.full.memberProperties

annotation class FieldName(val chinese: String, val english: String)

// 取得欄位的英文名稱的函數
inline fun <reified T : Any> getEnglishFieldName(chineseFieldName: String): String? {
    val properties = T::class.memberProperties
    for (property in properties) {
        val annotation = property.annotations.find { it is FieldName && it.chinese == chineseFieldName }
        if (annotation != null && annotation is FieldName) {
            return annotation.english
        }
    }
    return null
}