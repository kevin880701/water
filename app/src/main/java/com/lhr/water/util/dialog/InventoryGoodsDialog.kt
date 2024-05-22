package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.lhr.water.R
import com.lhr.water.databinding.DialogMaterialBinding
import com.lhr.water.util.widget.MaterialWidget
import org.json.JSONObject

class InventoryGoodsDialog(
    formItemFieldNameList: ArrayList<String>,
    formItemFieldNameEngList: ArrayList<String>,
    listener: Listener,
    var formItemFieldContentList: ArrayList<String>? = null,
    var formGoodsDataWidget: MaterialWidget? = null
) : DialogFragment() {

    private var dialog: AlertDialog? = null
    private var listener = listener
    private var formItemFieldNameList = formItemFieldNameList
    private var formItemFieldNameEngList = formItemFieldNameEngList

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogMaterialBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.dialog_material,
            null,
            false
        )
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        binding.widgetTitleBar.imageCancel.setOnClickListener(View.OnClickListener {
            this.dismiss()
        })
        initView(binding)
        builder.setView(binding.root)


        dialog = builder.create()
        return builder.create()
    }

    fun initView(binding: DialogMaterialBinding) {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.goods_information)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE
        initGoodsData(binding)
    }

    fun initGoodsData(binding: DialogMaterialBinding) {
//        formItemFieldNameList.forEachIndexed { index, fieldName ->
//            if (fieldName == "實盤數量") {
//                val formInputDataWidgetView = FormContentDataWidget(
//                    activity = requireActivity(),
//                    fieldName = fieldName,
//                    fieldContent = formItemFieldContentList!![index],
//                    isEnable = true,
//                    inputType = InputType.TYPE_CLASS_NUMBER
//                )
//                binding.linearData.addView(formInputDataWidgetView)
//            }else{
//                val formInputDataWidgetView = FormContentDataWidget(
//                    activity = requireActivity(),
//                    fieldName = fieldName,
//                    fieldContent = formItemFieldContentList!![index]
//                )
//                binding.linearData.addView(formInputDataWidgetView)
//            }
//        }
    }

    interface Listener {
        fun onChangeGoodsInfo(formItemJson: JSONObject, formGoodsDataWidget: MaterialWidget)
    }
}