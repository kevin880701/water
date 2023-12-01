package com.lhr.water.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.json.JSONObject
import timber.log.Timber


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


