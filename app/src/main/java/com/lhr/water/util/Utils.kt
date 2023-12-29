package com.lhr.water.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lhr.water.model.LoginData
import com.lhr.water.util.FormName.deliveryFormName
import com.lhr.water.util.FormName.pickingFormName
import com.lhr.water.util.FormName.returningFormName
import com.lhr.water.util.FormName.transferFormName
import com.lhr.water.util.TransferStatus.notTransfer
import com.lhr.water.util.TransferStatus.transferInput
import com.lhr.water.util.TransferStatus.transferOutput
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


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
 * 取得當前民國年月日
 * @return String
 */
fun getCurrentDate(): String {
    // 入庫時間記錄到年月日就好
    val recordDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    val time: String = recordDate.format(Date())
    // 解析原始日期
    val date = recordDate.parse(time)

    // 將日期轉換為 Calendar 對象
    val calendar = Calendar.getInstance()
    calendar.time = date

    // 獲取西元年
    val yearAD = calendar.get(Calendar.YEAR)

    // 將年份轉換為民國年，確保是三位數
    val yearROC = String.format("%03d", yearAD - 1911)

    // 獲取月份和日期，確保是兩位數
    val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
    val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))

    // 輸出結果
    return "$yearROC/$month/$day"
}


/**
 * 判斷表單是否是進貨
 * @return formContentJsonObject 表單JSON
 */
fun isInput(formContentJsonObject: JSONObject): Boolean{
    if(formContentJsonObject.getString("reportTitle") == deliveryFormName ||
        formContentJsonObject.getString("reportTitle") == returningFormName
    ){
        return true
    }else if(formContentJsonObject.getString("reportTitle") == pickingFormName){
        return false
    }else if(formContentJsonObject.getString("reportTitle") == transferFormName){
        return formContentJsonObject.getString("receivingDept") == LoginData.region && formContentJsonObject.getString("receivingLocation") == LoginData.map
    }else{
        return true
    }
}


/**
 * 判斷調撥單是否是進貨
 * @return formContentJsonObject 表單JSON
 */
fun transferStatus(isTransferForm: Boolean, jsonObject: JSONObject): String{
    return if(isTransferForm){
        if (jsonObject.getString("receivingDept") == LoginData.region && jsonObject.getString("receivingLocation") == LoginData.map){
            transferInput
        }else{
            transferOutput
        }
    }else{
        notTransfer
    }
}


