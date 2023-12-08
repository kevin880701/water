package com.lhr.water.util.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.repository.FormRepository
import com.lhr.water.databinding.WidgetFilterItemBinding
import com.lhr.water.ui.history.HistoryViewModel

class FilterItemWidget : RelativeLayout, View.OnClickListener {
    private var viewModel: HistoryViewModel
    var binding: WidgetFilterItemBinding
    private val activity: Activity
    private val itemName: String
    private val checkBoxItem: CheckBox
    private val textItemName: TextView
    private val constraintCheck: ConstraintLayout

    constructor(
        activity: Activity,
        fieldName: String,
        viewModel: HistoryViewModel
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_filter_item, this, true
        )
        this@FilterItemWidget.activity = activity
        this@FilterItemWidget.itemName = fieldName
        this@FilterItemWidget.viewModel = viewModel
        checkBoxItem = binding.checkBoxItem
        textItemName = binding.textItemName
        constraintCheck = binding.constraintCheck

        initView()
    }

    fun initView() {
        // 第一次創建讓勾選框被勾選
        if (FormRepository.getInstance(activity).filterList.value?.contains(itemName) == true) {
            checkBoxItem.isChecked = true
        }
        textItemName.text = itemName

        checkBoxItem.setOnCheckedChangeListener { buttonView, isChecked ->
            // 處理 CheckBox 的狀態變化
            if (isChecked) {
                // CheckBox 被選中
                FormRepository.getInstance(activity).filterList.value?.add(itemName)
                FormRepository.getInstance(activity).filterList.postValue(FormRepository.getInstance(activity).filterList.value)
            } else {
                // CheckBox 被取消選中
                FormRepository.getInstance(activity).filterList.value?.removeIf { it == itemName }
                FormRepository.getInstance(activity).filterList.postValue(FormRepository.getInstance(activity).filterList.value)
            }
        }
        constraintCheck.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.constraintCheck -> {
                checkBoxItem.isChecked = !checkBoxItem.isChecked
            }
        }
    }
}