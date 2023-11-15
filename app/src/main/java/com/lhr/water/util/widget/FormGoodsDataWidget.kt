package com.lhr.water.util.widget

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetFormGoodsBinding
import com.lhr.water.util.jsonObjectContentToList
import org.json.JSONObject

class FormGoodsDataWidget : RelativeLayout {
    var binding: WidgetFormGoodsBinding
    private val activity: Activity
    var formItemJson: JSONObject
    private val textGoodsName: TextView
    private val textGoodsNumber: TextView
    constructor(
        activity: Activity,
        formItemFieldJson: JSONObject,
        listener: Listener? = null,
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_form_goods, this, true
        )
        this@FormGoodsDataWidget.activity = activity
        this@FormGoodsDataWidget.formItemJson = formItemFieldJson
        textGoodsName = binding.textGoodsName
        textGoodsNumber = binding.textGoodsNumber

        binding.imageDelete.setOnClickListener {
            listener?.onDeleteGoodsClick(binding.imageDelete)
        }
        binding.constraintGoods.setOnClickListener {
            listener?.onGoodsColClick(jsonObjectContentToList(this@FormGoodsDataWidget.formItemJson), this)
        }
        initView()
    }

    fun initView(){
        Log.v("AAAA","" + formItemJson.toString())
        textGoodsName.text = formItemJson.getString("MaterialName")
        textGoodsNumber.text = formItemJson.getString("MaterialNumber")
    }

    interface Listener{
        fun onDeleteGoodsClick(view: View)
        fun onGoodsColClick(formItemFieldContentList: ArrayList<String>, formGoodsDataWidget: FormGoodsDataWidget)
    }
}