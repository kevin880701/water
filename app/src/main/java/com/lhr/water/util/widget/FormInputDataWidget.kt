package com.lhr.water.util.widget

import android.app.Activity
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetFormContentBinding

class FormInputDataWidget : RelativeLayout {
    private var binding: WidgetFormContentBinding
    private val activity: Activity
    private val dataName: String
    private var dataContent: String? = null
    private val textDataName: TextView
    private val editDataContent: EditText
    private val isDeleteVisible: Boolean
    constructor(
        activity: Activity,
        dataName: String,
        isDeleteVisible: Boolean,
        listener: Listener? = null,
        dataContent: String? = null
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_content, this, true
        )
        this@FormInputDataWidget.activity = activity
        this@FormInputDataWidget.dataName = dataName
        this@FormInputDataWidget.dataContent = dataContent
        this@FormInputDataWidget.isDeleteVisible = isDeleteVisible
        textDataName = binding.textDataName
        editDataContent = binding.editDataContent

        initView()
    }

    fun initView(){
        textDataName.text = dataName
        dataContent?.let {
            editDataContent.text = Editable.Factory.getInstance().newEditable(dataContent)
        }
    }

    interface Listener{
        fun onDeleteGoodsClick(view: View)
    }
}