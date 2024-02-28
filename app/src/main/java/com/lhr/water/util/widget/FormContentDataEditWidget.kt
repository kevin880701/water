package com.lhr.water.util.widget

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetFormEditBinding

class FormContentDataEditWidget : RelativeLayout {
    var binding: WidgetFormEditBinding
    private val activity: Activity
    private val fieldName: String
    private var fieldContent: String? = null
    private val textDataName: TextView
    private val editDataContent: EditText
    var content = "待處理"

    constructor(
        activity: Activity,
        fieldName: String,
        fieldContent: String? = null
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_edit, this, true
        )
        this@FormContentDataEditWidget.activity = activity
        this@FormContentDataEditWidget.fieldName = fieldName
        this@FormContentDataEditWidget.fieldContent = fieldContent
        textDataName = binding.textDataName
        editDataContent = binding.editDataContent

        initView()
    }

    fun initView() {
        textDataName.text = fieldName
        editDataContent.text = Editable.Factory.getInstance().newEditable(fieldContent)
        binding.editDataContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本改變之前執行的操作
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 在文本改變過程中執行的操作
                content = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本改變之後執行的操作
                content = s.toString()
            }
        })
    }
}