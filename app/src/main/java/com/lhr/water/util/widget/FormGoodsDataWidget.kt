package com.lhr.water.util.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetFormMaterialBinding
import com.lhr.water.util.manager.jsonObjectContentToList
import org.json.JSONObject

class FormGoodsDataWidget : RelativeLayout {
    var binding: WidgetFormMaterialBinding
    private val activity: Activity
    var formItemJson: JSONObject
    private val textMaterialName: TextView
    private val textMaterialNumber: TextView
    private val textMaterialSpec: TextView
    private val textMaterialUnit: TextView
    constructor(
        activity: Activity,
        formItemFieldJson: JSONObject,
        listener: Listener? = null,
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_material, this, true
        )
        this@FormGoodsDataWidget.activity = activity
        this@FormGoodsDataWidget.formItemJson = formItemFieldJson
        textMaterialName = binding.textMaterialName
        textMaterialNumber = binding.textMaterialNumber
        textMaterialSpec = binding.textMaterialSpec
        textMaterialUnit = binding.textMaterialUnit

        binding.imageDelete.setOnClickListener {
            listener?.onDeleteGoodsClick(binding.imageDelete)
        }
        binding.constraintGoods.setOnClickListener {
            listener?.onGoodsColClick(jsonObjectContentToList(this@FormGoodsDataWidget.formItemJson), this)
        }
        initView()
    }

    fun initView(){
        textMaterialName.text = formItemJson.getString("materialName")
        textMaterialNumber.text = formItemJson.getString("materialNumber")
        textMaterialSpec.text = formItemJson.getString("materialSpec")
        textMaterialUnit.text = formItemJson.getString("materialUnit")
    }

    interface Listener{
        fun onDeleteGoodsClick(view: View)
        fun onGoodsColClick(formItemFieldContentList: ArrayList<String>, formGoodsDataWidget: FormGoodsDataWidget)
    }
}