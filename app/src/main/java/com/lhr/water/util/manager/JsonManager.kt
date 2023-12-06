package com.lhr.water.util.manager

import android.content.Context
import com.lhr.water.R
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

/**
 * 將List轉為JSON格式的字串
 * @param fieldNameList 欄位名稱
 * @param fieldContent 欄位內容
 */
fun listToJsonString(
    fieldNameList: ArrayList<String>,
    fieldContentList: ArrayList<String>
): String {
    // 創建一個空的JSON對象
    val jsonObject = JSONObject()

    // 遍歷FieldName和FieldContent，將它們添加到JSON對象中
    for (i in 0 until fieldNameList.size) {
        val fieldName = fieldNameList[i]
        val fieldContent = if (i < fieldContentList.size) fieldContentList[i] else ""
        jsonObject.put(fieldName, fieldContent)
    }
    // 將JSON對象轉換為JSON字元串並輸出
    return jsonObject.toString()
}


/**
 * 將List轉為JSON物件
 * @param fieldNameList 欄位名稱
 * @param fieldContentList 欄位內容
 */
fun listToJsonObject(
    fieldNameList: ArrayList<String>,
    fieldContentList: ArrayList<String>
): JSONObject {
    // 創建一個空的JSON對象
    val jsonObject = JSONObject()

    // 遍歷FieldName和FieldContent，將它們添加到JSON對象中
    for (i in 0 until fieldNameList.size) {
        val fieldName = fieldNameList[i]
//        Timber.d(fieldName)
        val fieldContent = if (i < fieldContentList.size) fieldContentList[i] else ""
        jsonObject.put(fieldName, fieldContent)
    }
    // 輸出JSON字元串
    return jsonObject
}

/**
 * 將jsonObject轉為JSON格式的字串
 * @param jsonObject JSON物件
 */
fun jsonObjectToJsonString(jsonObject: JSONObject): String {
    return jsonObject.toString()
}

/**
 * 將JSON物件轉的欄位內容單獨取出並轉成ArrayList<String>
 * @param json JSON物件
 */
fun jsonObjectContentToList(json: JSONObject): ArrayList<String> {
    // 創建一個空的 ArrayList 用於存儲欄位內容
    val jsonContentList = ArrayList<String>()
    // 遍歷 JSON 對象的所有鍵
    for (key in json.keys()) {
        // 根據key獲取值
        val value = json.getString(key)
        // 將值添加到 ArrayList
        jsonContentList.add(value)
    }
    return jsonContentList
}

/**
 * 將JSON格式的字串轉為JSON物件
 * @param jsonString JSON格式的字串
 */
fun jsonStringToJson(jsonString: String): JSONObject {
    // 將 JSON 字串轉換為 JSONObject
    return JSONObject(jsonString)
}

/**
 * 將JSON格式的字串轉為JSONArray
 * @param jsonString JSON格式的字串
 */
fun jsonStringToJsonArray(jsonString: String): JSONArray {
    // 將 JSON 字串轉換為 JSONArray
    return JSONArray(jsonString)
}


/**
 * 在匯入資料後需要補齊一些缺少的欄位，目前是補["formClass"],["DealStatus"]，交貨要再補["DeliveryStatus"]
 * @param jsonArray 要補齊的JSONArray
 */fun jsonAddInformation(jsonArray: JSONArray,context: Context): JSONArray {
    var jsonArray = jsonArray
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)

        // 根據 "reportTitle" 進行判斷
//        val reportTitle = jsonObject.optString("reportTitle", "")
//        if (reportTitle.contains("交貨")) {
//            jsonObject.put("deliveryStatus", "")
//            jsonObject.put("deliveryDay", "")
//        }
    }
    return jsonArray
}
