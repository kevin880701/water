package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.lhr.water.R
import com.lhr.water.databinding.DialogFormGoodsBinding
import com.lhr.water.util.manager.listToJsonObject
import com.lhr.water.util.widget.FormContentDataEditWidget
import com.lhr.water.util.widget.FormGoodsDataWidget
import com.lhr.water.util.widget.FormContentDataWidget
import org.json.JSONObject
import timber.log.Timber

class InventoryGoodsDialog(
    formItemFieldNameList: ArrayList<String>,
    formItemFieldNameEngList: ArrayList<String>,
    listener: Listener,
    var formItemFieldContentList: ArrayList<String>? = null,
    var formGoodsDataWidget: FormGoodsDataWidget? = null
) : DialogFragment() {

    private var dialog: AlertDialog? = null
    private var listener = listener
    private var formItemFieldNameList = formItemFieldNameList
    private var formItemFieldNameEngList = formItemFieldNameEngList

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogFormGoodsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.dialog_form_goods,
            null,
            false
        )
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        binding.buttonConfirm.setOnClickListener {
            var formItemFieldContentList = ArrayList<String>()
            for (i in 0 until binding.linearData.childCount) {
                val linearLayoutItem = binding.linearData.getChildAt(i)
                val editText = linearLayoutItem.findViewById<EditText>(R.id.textDataContent)
                val editTextValue = editText.text.toString()
                formItemFieldContentList.add(editTextValue)
            }
            formGoodsDataWidget?.let { it ->
                listener.onChangeGoodsInfo(listToJsonObject(formItemFieldNameEngList, formItemFieldContentList),
                    it
                )
            }
            this.dismiss()
        }
        binding.widgetTitleBar.imageCancel.setOnClickListener(View.OnClickListener {
            this.dismiss()
        })
        initView(binding)
        builder.setView(binding.root)


        dialog = builder.create()
//        Objects.requireNonNull(dialog?.window)
//            ?.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog?.show()
//        val lp = WindowManager.LayoutParams()
//        val dm = DisplayMetrics()
//        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
//        lp.copyFrom(dialog?.window!!.attributes)
//        lp.width = (dm.widthPixels * 0.7).toInt()
//        dialog?.window!!.attributes = lp
        return builder.create()
    }

    fun initView(binding: DialogFormGoodsBinding) {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.goods_information)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE
        initGoodsData(binding)

    }

    fun initGoodsData(binding: DialogFormGoodsBinding) {
        var dataNameList =
            activity?.resources?.getStringArray(R.array.delivery_Item_field_name)?.toList() as ArrayList<String>

        Timber.d("@@@@：${formItemFieldContentList!!.size}")
        formItemFieldNameList.forEachIndexed { index, fieldName ->
            if (fieldName == "實盤數量") {
                val formContentDataEditWidget = FormContentDataEditWidget(
                    activity = requireActivity(),
                    fieldName = fieldName,
                    fieldContent = formItemFieldContentList!![index])
                binding.linearData.addView(formContentDataEditWidget)
            }else{
                val formInputDataWidgetView = FormContentDataWidget(
                    activity = requireActivity(),
                    fieldName = fieldName,
                    fieldContent = formItemFieldContentList!![index]
                )
                binding.linearData.addView(formInputDataWidgetView)
            }
        }
    }

    interface Listener {
        fun onChangeGoodsInfo(formItemJson: JSONObject, formGoodsDataWidget: FormGoodsDataWidget)
    }
}