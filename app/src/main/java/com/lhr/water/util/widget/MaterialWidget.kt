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

class MaterialWidget : RelativeLayout {
    var binding: WidgetFormMaterialBinding
    private val activity: Activity
    var itemDetail: BaseItem
    private val textMaterialName: TextView
    private val textRequestQuantity: TextView
    private val textApprovedQuantity: TextView
    var isDeliveryStatus: Boolean = false
    constructor(
        activity: Activity,
        itemDetail: BaseItem,
        deliveryStatus: String? = null
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_material, this, true
        )
        this@MaterialWidget.activity = activity
        this@MaterialWidget.itemDetail = itemDetail

        if(deliveryStatus == "true"){
            binding.switchDeliveryStatus.isChecked = true
            isDeliveryStatus = true
        }else{
            binding.switchDeliveryStatus.isChecked = false
            isDeliveryStatus = false
        }
        if(deliveryStatus == null){
            binding.switchDeliveryStatus.visibility = View.GONE
        }

        textMaterialName = binding.textMaterialName
        textRequestQuantity = binding.textRequestQuantity
        textApprovedQuantity = binding.textApprovedQuantity

        initView()
    }

    fun initView(){
        textMaterialName.text = itemDetail.materialName
        textRequestQuantity.text = itemDetail.getRequestQuantity().toString()
        textApprovedQuantity.text = itemDetail.getApprovedQuantity().toString()

        binding.switchDeliveryStatus.setOnCheckedChangeListener { _, isChecked ->
            isDeliveryStatus = isChecked
        }
    }
}