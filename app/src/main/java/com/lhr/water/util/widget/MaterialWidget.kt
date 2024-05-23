package com.lhr.water.util.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.data.form.BaseForm
import com.lhr.water.data.form.BaseItem
import com.lhr.water.databinding.WidgetFormMaterialBinding
import com.lhr.water.ui.formContent.FormContentActivity

class MaterialWidget : RelativeLayout {
    var binding: WidgetFormMaterialBinding
    private val activity: FormContentActivity
    var itemDetail: BaseItem
    var baseForm: BaseForm
    private val textMaterialName: TextView
    private val textRequestQuantity: TextView
    val textApprovedQuantity: TextView
    var dealStatus = ""
    constructor(
        activity: FormContentActivity,
        baseForm: BaseForm,
        itemDetail: BaseItem,
        deliveryStatus: String? = null,
        dealStatus: String
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_material, this, true
        )
        this@MaterialWidget.activity = activity
        this@MaterialWidget.itemDetail = itemDetail
        this@MaterialWidget.baseForm = baseForm
        this@MaterialWidget.dealStatus = dealStatus

        binding.switchDeliveryStatus.isChecked = deliveryStatus == "true"

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
        textRequestQuantity.text = itemDetail.requestQuantity

        textApprovedQuantity.text = if(dealStatus == "處理完成") itemDetail.approvedQuantity else calculateApprovedQuantity(baseForm.formNumber, itemDetail.materialName, itemDetail.materialNumber)
    }

    fun calculateApprovedQuantity(
        formNumber: String,
        materialName: String,
        materialNumber: String): String{
        return activity.viewModel.formRepository.tempStorageRecordEntities.value!!.filter {
            it.formNumber == formNumber &&
                    it.materialName == materialName &&
                    it.materialNumber == materialNumber
        }.sumOf {
            it.quantity
        }.toString()
    }
}