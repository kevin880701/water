package com.lhr.water.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.lhr.water.data.form.TransferForm
import com.lhr.water.room.FormEntity
import com.lhr.water.data.LoginData
import com.lhr.water.util.FormName.deliveryFormName
import com.lhr.water.util.FormName.pickingFormName
import com.lhr.water.util.FormName.returningFormName
import com.lhr.water.util.FormName.transferFormName
import com.lhr.water.util.TransferStatus.notTransfer
import com.lhr.water.util.TransferStatus.transferInput
import com.lhr.water.util.TransferStatus.transferOutput
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.reflect.full.memberProperties


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
 * 取得當前西元年月日
 * @return String
 */
fun getCurrentDate(): String {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
    val currentTime = Date()
    return dateFormat.format(currentTime)
}

fun convertToRocDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yy/MM/dd", Locale.getDefault())

    val date = inputFormat.parse(inputDate)
    val calendar = Calendar.getInstance()
    calendar.time = date!!

    val taiwanYear = calendar.get(Calendar.YEAR) - 1911
    val taiwanDate = outputFormat.format(date)
    return taiwanDate.replaceFirst(Regex("\\d+"), taiwanYear.toString())
}


/**
 * 判斷表單是否是進貨
 * @return formContentJsonObject 表單JSON
 */
fun isInput(formEntity: FormEntity): Boolean{
    val gson = Gson()
    if(formEntity.reportTitle == deliveryFormName ||
        formEntity.reportTitle == returningFormName
    ){
        return true
    }else if(formEntity.reportTitle == pickingFormName){
        return false
    }else if(formEntity.reportTitle == transferFormName){
        val transferForm: TransferForm = gson.fromJson(formEntity.formContent, TransferForm::class.java)
        return transferForm.transferStatus == "撥方已送出"
    }else{
        return true
    }
}


/**
 * 判斷調撥單是否是進貨
 * @return formContentJsonObject 表單JSON
 */
fun transferStatus(isTransferForm: Boolean, formEntity: FormEntity): String{

    val gson = Gson()
    val transferForm: TransferForm = gson.fromJson(formEntity.formContent, TransferForm::class.java)
    return if(isTransferForm){
        if (transferForm.receivingDept == LoginData.region && transferForm.receivingLocation == LoginData.map){
            transferInput
        }else{
            transferOutput
        }
    }else{
        notTransfer
    }
}

/**
 * 設定屬性值的函數
 * @param obj
 * @param propertyName CLASS的變數名稱
 * @param value 要設定的值
 */
fun setPropertyValue(obj: Any, propertyName: String, value: Any?) {
    val property = obj::class.memberProperties.find { it.name == propertyName }
    if (property != null && property.returnType.isMarkedNullable) {
        (property as? kotlin.reflect.KMutableProperty<*>)?.setter?.call(obj, value)
    }
}


