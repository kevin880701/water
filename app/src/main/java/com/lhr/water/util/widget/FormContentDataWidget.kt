package com.lhr.water.util.widget

import android.app.Activity
import android.text.Editable
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

class FormContentDataWidget : RelativeLayout {
    var binding: WidgetFormContentBinding
    private val activity: Activity
    private val fieldName: String
    private var fieldContent: String? = null
    private val textDataName: TextView
    private val editDataContent: EditText
    var content = ""
    constructor(
        activity: Activity,
        fieldName: String,
        fieldContent: String? = null
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_content, this, true
        )
        this@FormContentDataWidget.activity = activity
        this@FormContentDataWidget.fieldName = fieldName
        this@FormContentDataWidget.fieldContent = fieldContent
        textDataName = binding.textDataName
        editDataContent = binding.editDataContent

        initView()
    }

    fun initView(){
        textDataName.text = fieldName
        fieldContent?.let {
            editDataContent.text = Editable.Factory.getInstance().newEditable(fieldContent)
            content = fieldContent as String
        }
        editDataContent.addTextChangedListener(object : TextWatcher {
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

    interface Listener{
        fun onDeleteGoodsClick(view: View)
    }
}