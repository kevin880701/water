package com.lhr.water.util.popupWindow

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import com.lhr.water.R
import com.lhr.water.databinding.PopupMarkNameBinding
import com.lhr.water.model.Model.Companion.markDrawableIdMap
import com.lhr.water.model.TargetData


class MarkInformationPopupWindow(mContext: Context, targetData: TargetData) : PopupWindow(), View.OnClickListener {
    var view: View
    var imageRoomMark: ImageView
    var textRoomName: TextView
    var imageInformation: ImageView
    var binding: PopupMarkNameBinding


    init {
        binding = PopupMarkNameBinding.inflate(LayoutInflater.from(mContext))
        view = binding.root
        imageRoomMark = binding.imageTargetMark
        textRoomName = binding.textTargetName
        imageInformation = binding.imageInformation
        textRoomName.text = targetData.targetName

        imageRoomMark.setImageDrawable(mContext.resources.getDrawable(markDrawableIdMap[targetData.targetTypeNum]!!, mContext.theme))

        // 外部可點擊
        this.isOutsideTouchable = true
        // mMenuView添加OnTouchListener監聽判斷獲取觸屏位置如果在選擇框外面則銷毀彈出框
        view.setOnTouchListener { v, event ->
            val height = binding.constrainBottom.top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss()
                }
            }
            true
        }
        this.contentView = view
        // 窗口高和寬填滿

        val metrics = mContext.resources.displayMetrics
        val heightPixels = metrics.heightPixels

        var result = 0
        var resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }

        this.height = RelativeLayout.LayoutParams.MATCH_PARENT
        this.height = heightPixels + result
        this.width = RelativeLayout.LayoutParams.MATCH_PARENT
        // 設置彈出窗體可點擊
        this.isFocusable = true
        // 背景色
//        val dw = ColorDrawable(-0x50000000)
//        setBackgroundDrawable(dw)
//        MainActivity.mainActivity.window.statusBarColor = MainActivity.themeColorArray.get(
//            MainActivity.themColorStatus *2 + 1)
        // 彈出窗体的動畫
        this.animationStyle = R.style.mark_popup_anim

        imageInformation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageInformation -> {
                Log.v("LPLPL","PLPLPLP")
                dismiss()
            }
        }
    }

}