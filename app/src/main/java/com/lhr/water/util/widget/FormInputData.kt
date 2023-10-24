package com.lhr.water.util.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetFormContentBinding
import com.lhr.water.ui.formContent.FormContentActivity

class FormInputData : RelativeLayout {
    private var binding: WidgetFormContentBinding
    private val activity: FormContentActivity
    private val dataName: String
    private val textDataName: TextView
    private val editDataContent: EditText
    private val isDeleteVisible: Boolean
    constructor(
        activity: FormContentActivity,
        dataName: String,
        isDeleteVisible: Boolean,
        listener: Listener? = null,
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_content, this, true
        )
        this@FormInputData.activity = activity
        this@FormInputData.dataName = dataName
        this@FormInputData.isDeleteVisible = isDeleteVisible
        textDataName = binding.textDataName
        editDataContent = binding.editDataContent

        binding.imageDelete.setOnClickListener {
            listener?.onDeleteGoodsClick(binding.imageDelete)
        }
        initView()
    }

    fun initView(){
        textDataName.text = dataName
        //判斷是否顯示刪除按鈕
        binding.imageDelete.visibility = if (isDeleteVisible) View.VISIBLE else View.GONE
    }

    interface Listener{
        fun onDeleteGoodsClick(view: View)
    }
}