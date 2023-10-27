package com.lhr.water.util.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.data.FormGoodInfo
import com.lhr.water.databinding.WidgetFormGoodsBinding

class FormGoodsDataWidget : RelativeLayout {
    var binding: WidgetFormGoodsBinding
    private val activity: Activity
    var formGoodInfo: FormGoodInfo
    private val textGoodsName: TextView
    private val textGoodsNumber: TextView
    constructor(
        activity: Activity,
        formGoodInfo: FormGoodInfo,
        listener: Listener? = null,
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_goods, this, true
        )
        this@FormGoodsDataWidget.activity = activity
        this@FormGoodsDataWidget.formGoodInfo = formGoodInfo
        textGoodsName = binding.textGoodsName
        textGoodsNumber = binding.textGoodsNumber

        binding.imageDelete.setOnClickListener {
            listener?.onDeleteGoodsClick(binding.imageDelete)
        }
        binding.constraintGoods.setOnClickListener {
            listener?.onGoodsColClick(this@FormGoodsDataWidget.formGoodInfo, this)
        }
        initView()
    }

    fun initView(){
        textGoodsName.text = formGoodInfo.goodsName
        textGoodsNumber.text = formGoodInfo.goodsNumber
    }

    interface Listener{
        fun onDeleteGoodsClick(view: View)
        fun onGoodsColClick(formGoodInfo: FormGoodInfo, formGoodsDataWidget: FormGoodsDataWidget)
    }
}