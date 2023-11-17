package com.lhr.water.util.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.databinding.WidgetFilterItemBinding
import com.lhr.water.ui.history.HistoryViewModel

class FilterItemWidget : RelativeLayout {
    private var viewModel: HistoryViewModel
    var binding: WidgetFilterItemBinding
    private val itemName: String
    private val checkBoxItem: CheckBox
    private val textItemName: TextView

    constructor(
        activity: Activity,
        fieldName: String,
        viewModel: HistoryViewModel
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_filter_item, this, true
        )
        this@FilterItemWidget.itemName = fieldName
        this@FilterItemWidget.viewModel = viewModel
        checkBoxItem = binding.checkBoxItem
        textItemName = binding.textItemName

        initView()
    }

    fun initView() {
        // 第一次創建讓勾選框被勾選
        if (viewModel.filterList.value?.contains(itemName) == true) {
            checkBoxItem.isChecked = true
        }
        textItemName.text = itemName

        checkBoxItem.setOnCheckedChangeListener { buttonView, isChecked ->
                // 处理 CheckBox 的状态变化
                if (isChecked) {
                    // CheckBox 被选中
                    viewModel.filterList.value?.add(itemName)
                    viewModel.filterList.postValue(viewModel.filterList.value)
                } else {
                    // CheckBox 被取消选中
                    viewModel.filterList.value?.removeIf { it == itemName }
                    viewModel.filterList.postValue(viewModel.filterList.value)
                }
            }
    }
}