package com.lhr.water.util.popupWindow

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.lhr.water.R
import com.lhr.water.databinding.PopupFilterMenuBinding
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.widget.FilterItemWidget


class FilterFormPopupWindow(activity: Activity, viewModel: HistoryViewModel) : PopupWindow(),
    View.OnClickListener {
    var binding: PopupFilterMenuBinding
    var activity = activity
    var viewModel = viewModel
    var view: View
    var linearItem: LinearLayout
    var itemList = ArrayList<String>()

    init {
        binding = PopupFilterMenuBinding.inflate(LayoutInflater.from(activity))
        view = binding.root
        linearItem = binding.linearItem

        // 外部可點擊
        this.isOutsideTouchable = true
        // 監聽判斷獲取觸屏位置如果在選擇框外面則銷毀彈出框
        view.setOnTouchListener { v, event ->
            val height = binding.linearItem.top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss()
                }
            }
            true
        }
        this.contentView = view
        // 設定高和寬
        this.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        this.width = RelativeLayout.LayoutParams.WRAP_CONTENT

        // 設定背景
        this.setBackgroundDrawable(ColorDrawable(Color.GRAY))
        // 可被點擊
        this.isFocusable = true
        // 設定動畫
        this.animationStyle = R.style.FilterMenuAnimation

        addItem()
    }


    /**
     * 根據form_array來增加要輸入的欄位
     */
    private fun addItem() {
        itemList = activity.resources.getStringArray(R.array.form_array)
            .toList() as ArrayList<String>
        itemList.forEachIndexed { index, fieldName ->
            // 創建FormContentDataWidget
            val filterItemWidget = FilterItemWidget(
                activity = activity,
                fieldName = fieldName,
                viewModel = viewModel
            )

            binding.linearItem.addView(filterItemWidget)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }

}