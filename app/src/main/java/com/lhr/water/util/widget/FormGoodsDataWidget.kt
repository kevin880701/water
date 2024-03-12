package com.lhr.water.util.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.data.ItemDetail
import com.lhr.water.databinding.WidgetFormMaterialBinding
import com.lhr.water.util.manager.jsonObjectContentToList
import org.json.JSONObject

class FormGoodsDataWidget : RelativeLayout {
    var binding: WidgetFormMaterialBinding
    private val activity: Activity
    var itemDetail: ItemDetail
    private val textMaterialName: TextView
    private val textMaterialNumber: TextView
    private val textMaterialSpec: TextView
    private val textMaterialUnit: TextView
    constructor(
        activity: Activity,
        itemDetail: ItemDetail,
        listener: Listener? = null,
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_material, this, true
        )
        this@FormGoodsDataWidget.activity = activity
        this@FormGoodsDataWidget.itemDetail = itemDetail
        textMaterialName = binding.textMaterialName
        textMaterialNumber = binding.textMaterialNumber
        textMaterialSpec = binding.textMaterialSpec
        textMaterialUnit = binding.textMaterialUnit

        binding.imageDelete.setOnClickListener {
            listener?.onDeleteGoodsClick(binding.imageDelete)
        }
        binding.constraintGoods.setOnClickListener {
            listener?.onGoodsColClick(this@FormGoodsDataWidget.itemDetail, this)
        }
        initView()
    }

    fun initView(){
        textMaterialName.text = itemDetail.materialName
        textMaterialNumber.text = itemDetail.materialNumber
        textMaterialSpec.text = itemDetail.materialSpec
        textMaterialUnit.text = itemDetail.materialUnit
    }

    interface Listener{
        fun onDeleteGoodsClick(view: View)
        fun onGoodsColClick(itemDetail: ItemDetail, formGoodsDataWidget: FormGoodsDataWidget)
    }
}