package com.lhr.water.util.manager

import android.content.Context
import com.lhr.water.util.FormField.formFieldMap
import com.lhr.water.util.showToast
import org.json.JSONArray
import org.json.JSONObject

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
 * 在匯入資料後需要補齊一些缺少的欄位，目前是補["DealStatus"]，交貨要再補["DeliveryStatus"]
 * @param jsonArray 要補齊的JSONArray
 */
fun jsonAddInformation(jsonArray: JSONArray, context: Context): JSONArray {
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

/**
 * 檢查匯入的json格式是否有問題
 * @param jsonArray 要檢查的JSONArray
 */
fun checkJson(jsonArray: JSONArray, context: Context): Boolean {
    // 遍歷每個 JSONObject
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        // 檢查是否存在 reportTitle 欄位
        if (jsonObject.has("reportTitle")) {
            // reportTitle 存在，檢查表單名稱是否正確
            if (formFieldMap.containsKey(jsonObject.getString("reportTitle"))) {
                // 檢查是否存在 formNumber 欄位，檢查formNumber是為了之後提示哪張表錯誤
                if (jsonObject.has("formNumber")) {
                    // 確認表單名稱和formNumber正確後，開始檢查欄位。只要有不符合的不待後續檢查馬上回傳FALSE
                    for (field in formFieldMap[jsonObject.getString("reportTitle")]!![0]) {
                        if (!jsonObject.has(field)) {
                            showToast(
                                context,
                                "表單代號${jsonObject.getString("formNumber")}：${field}欄位錯誤"
                            )
                            return false
                        }
                    }
                } else {
                    showToast(context, "formNumber欄位錯誤")
                    return false
                }
            } else {
                showToast(context, "表單名稱錯誤")
                return false
            }
        } else {
            showToast(context, "reportTitle欄位錯誤")
            return false
        }
    }
    return true
}
