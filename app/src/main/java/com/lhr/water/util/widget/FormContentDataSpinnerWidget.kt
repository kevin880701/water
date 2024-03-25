package com.lhr.water.util.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetFormContentSpinnerBinding
import com.lhr.water.util.adapter.SpinnerAdapter
import com.lhr.water.util.interfaces.FormContentData

class FormContentDataSpinnerWidget : RelativeLayout, FormContentData {
    var binding: WidgetFormContentSpinnerBinding
    private val activity: Activity
    override var fieldName: String
    override var fieldEngName: String
    private val spinnerList: ArrayList<String>
    private var fieldContent: String? = null
    private val textDataName: TextView
    private val spinnerDataContent: Spinner
    override var content = "待處理"

    constructor(
        activity: Activity,
        spinnerList: ArrayList<String>,
        fieldName: String,
        fieldEngName: String,
        fieldContent: String? = null
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_content_spinner, this, true
        )
        this@FormContentDataSpinnerWidget.activity = activity
        this@FormContentDataSpinnerWidget.spinnerList = spinnerList
        this@FormContentDataSpinnerWidget.fieldName = fieldName
        this@FormContentDataSpinnerWidget.fieldEngName = fieldEngName
        this@FormContentDataSpinnerWidget.fieldContent = fieldContent
        textDataName = binding.textDataName
        spinnerDataContent = binding.spinnerDataContent

        initView()
    }

    fun initView() {
        textDataName.text = fieldName
        val adapter = SpinnerAdapter(activity, android.R.layout.simple_spinner_item, spinnerList)
        spinnerDataContent.adapter = adapter

        // 設定Spinner的選擇項監聽器
        fieldContent?.let {
            spinnerDataContent.setSelection(spinnerList.indexOf(fieldContent))
            content = fieldContent.toString()
        }
        spinnerDataContent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 當選項被選擇時，將選項的值存儲到content
                content = spinnerList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 如果沒有選項被選擇，你可以在這里處理邏輯
            }
        }

    }
}