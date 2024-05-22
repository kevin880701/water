package com.lhr.water.util.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.data.form.BaseItem
import com.lhr.water.databinding.WidgetFormMaterialBinding

class FormGoodsDataWidget : RelativeLayout {
    var binding: WidgetFormMaterialBinding
    private val activity: Activity
    var itemDetail: BaseItem
    private val textMaterialName: TextView
    private val textMaterialNumber: TextView
    private val textRequestQuantity: TextView
    private val textApprovedQuantity: TextView
    constructor(
        activity: Activity,
        itemDetail: BaseItem,
        listener: Listener? = null,
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_material, this, true
        )
        this@FormGoodsDataWidget.activity = activity
        this@FormGoodsDataWidget.itemDetail = itemDetail
        textMaterialName = binding.textMaterialName
        textMaterialNumber = binding.textMaterialNumber
        textRequestQuantity = binding.textRequestQuantity
        textApprovedQuantity = binding.textApprovedQuantity

        binding.constraintMaterial.setOnClickListener {
            listener?.onGoodsColClick(this@FormGoodsDataWidget.itemDetail, this)
        }
        initView()
    }

    fun initView(){
        textMaterialName.text = itemDetail.materialName
        textMaterialNumber.text = itemDetail.materialNumber
        textRequestQuantity.text = itemDetail.getRequestQuantity().toString()
        textApprovedQuantity.text = itemDetail.getApprovedQuantity().toString()
    }

    interface Listener{
        fun onDeleteGoodsClick(view: View)
        fun onGoodsColClick(itemDetail: BaseItem, formGoodsDataWidget: FormGoodsDataWidget)
    }
}