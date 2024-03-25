package com.lhr.water.util.widget

import android.app.Activity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetFormContentBinding
import com.lhr.water.util.interfaces.FormContentData

class FormContentDataWidget : RelativeLayout, FormContentData {
    var binding: WidgetFormContentBinding
    private val activity: Activity
    override var fieldName = ""
    override var fieldEngName = ""
    private var fieldContent: String? = null
    private var isEnable: Boolean? = null
    private var inputType: Int? = null
    private val textDataName: TextView
    private val textDataContent: EditText
    override var content = ""

    constructor(
        activity: Activity,
        fieldName: String,
        fieldEngName: String,
        fieldContent: String? = null,
        isEnable: Boolean? = false,
        inputType: Int? = InputType.TYPE_CLASS_TEXT
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_content, this, true
        )
        this@FormContentDataWidget.activity = activity
        this@FormContentDataWidget.fieldName = fieldName
        this@FormContentDataWidget.fieldEngName = fieldEngName
        this@FormContentDataWidget.fieldContent = fieldContent
        this@FormContentDataWidget.isEnable = isEnable
        this@FormContentDataWidget.inputType = inputType
        textDataName = binding.textDataName
        textDataContent = binding.textDataContent

        initView()
    }

    fun initView() {
        textDataName.text = fieldName
        fieldContent?.let {
            textDataContent.text = Editable.Factory.getInstance().newEditable(fieldContent)
            content = fieldContent as String
        }
        textDataContent.inputType = inputType!!
        textDataContent.isEnabled = isEnable!!
        textDataContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本變化之前執行的操作
                Log.v("QQQQQQQQbeforeTextChanged", "" + s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 文本變化時執行的操作
                Log.v("QQQQQQQQonTextChanged", "" + s)
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本變化之後執行的操作
                Log.v("QQQQQQQQafterTextChanged", "" + s)
                content = s.toString()
            }
        })
    }

    interface Listener {
        fun onDeleteGoodsClick(view: View)
    }
}