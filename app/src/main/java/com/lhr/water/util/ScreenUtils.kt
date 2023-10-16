package com.lhr.water.util

import android.content.Context
import android.util.TypedValue

// 單位轉換
object ScreenUtils {

    fun dp2px(context: Context, dp: Float): Float {
        val displayMetrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
    }

    fun dp2px(context: Context, dp: Int): Int{
        return dp2px(context, dp.toFloat()).toInt()
    }


}