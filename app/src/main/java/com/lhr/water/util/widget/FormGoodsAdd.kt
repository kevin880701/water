package com.lhr.water.util.widget

import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetFormContentBinding
import com.lhr.water.databinding.WidgetFormGoodsAddBinding
import com.lhr.water.manager.DialogManager
import com.lhr.water.ui.formContent.FormContentActivity
import com.lhr.water.util.recyclerViewAdapter.FormAdapter

class FormGoodsAdd : RelativeLayout {
    var binding: WidgetFormGoodsAddBinding
    private val activity: FormContentActivity
    constructor(
        activity: FormContentActivity,
        listener: Listener,
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_goods_add, this, true
        )
        this@FormGoodsAdd.activity = activity

        binding.imageAdd.setOnClickListener {
            listener.onAddGoodsClick()
        }
        initView()
    }

    fun initView(){
    }


    interface Listener{
        fun onAddGoodsClick()
    }
}