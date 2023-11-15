package com.lhr.water.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.json.JSONObject


/**
 * 以Glide載入圖片
 * @param imageFile 載入的圖片
 * @param imageView 要載入圖片的元件
 */
fun ImageView.loadImageWithGlide(imageFile: Uri, imageView: ImageView) {
    Glide.with(this)
        .load(imageFile)
        .diskCacheStrategy(DiskCacheStrategy.NONE) // 禁用磁盤緩存
        .skipMemoryCache(true) // 禁用內存緩存
        .into(imageView)
}

/**
 * 顯示Toast通知
 * @param context
 * @param message 要顯示的文字
 */
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


/**
 * 將List轉為JSON格式的字串
 * @param fieldNameList 欄位名稱
 * @param fieldContent 欄位內容
 */
fun listToJsonString(fieldNameList: ArrayList<String>, fieldContentList: ArrayList<String>): String {
    // 創建一個空的JSON對象
    val jsonObject = JSONObject()

    // 遍歷FieldName和FieldContent，將它們添加到JSON對象中
    for (i in 0 until fieldNameList.size) {
        val fieldName = fieldNameList[i]
        val fieldContent = if (i < fieldContentList.size) fieldContentList[i] else ""
        jsonObject.put(fieldName, fieldContent)
    }

    // 將JSON對象轉換為JSON字元串
    val jsonString = jsonObject.toString()

    // 輸出JSON字元串
    return jsonString
}


/**
 * 將List轉為JSON物件
 * @param fieldNameList 欄位名稱
 * @param fieldContentList 欄位內容
 */
fun listToJsonObject(fieldNameList: ArrayList<String>, fieldContentList: ArrayList<String>): JSONObject {
    // 創建一個空的JSON對象
    val jsonObject = JSONObject()

    // 遍歷FieldName和FieldContent，將它們添加到JSON對象中
    for (i in 0 until fieldNameList.size) {
        val fieldName = fieldNameList[i]
        val fieldContent = if (i < fieldContentList.size) fieldContentList[i] else ""
        jsonObject.put(fieldName, fieldContent)
        Log.v("QQQQQQQQQQQ","" + fieldName)
        Log.v("QQQQQQQQQQQ","" + fieldContent)
    }

    // 輸出JSON字元串
    return jsonObject
}

/**
 * 將List轉為JSON物件
 * @param jsonObject JSON物件
 */
fun jsonObjectToJsonString(jsonObject: JSONObject): String {
    return jsonObject.toString()
}

/**
 * 將JSON格式的字串轉為JSON物件
 * @param jsonString JSON格式的字串
 */
fun jsonStringToJson(jsonString: String): JSONObject {
    // 將 JSON 字元串轉換為 JSONObject
    return JSONObject(jsonString)
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

