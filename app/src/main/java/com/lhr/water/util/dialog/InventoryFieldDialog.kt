package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.lhr.water.R
import com.lhr.water.data.InventoryForm
import com.lhr.water.data.ItemDetail
import com.lhr.water.databinding.DialogFormGoodsBinding
import com.lhr.water.util.widget.FormContentDataWidget
import com.lhr.water.util.widget.FormGoodsDataWidget
import org.json.JSONObject

class InventoryFieldDialog(
    var listener: Listener,
    var formFieldNameMap: MutableMap<String, String>,
    var inventoryForm: InventoryForm? = null,
) : DialogFragment() {
    private var dialog: AlertDialog? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogFormGoodsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.dialog_form_goods,
            null,
            false
        )
        println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ")
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        binding.buttonConfirm.setOnClickListener {
            this.dismiss()
        }

        binding.widgetTitleBar.imageCancel.setOnClickListener(View.OnClickListener {
            this.dismiss()
        })

        initView(binding)
        builder.setView(binding.root)


        dialog = builder.create()
        return builder.create()
    }

    fun initView(binding: DialogFormGoodsBinding) {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.goods_information)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE
        initGoodsData(binding)
    }


    fun initGoodsData(binding: DialogFormGoodsBinding) {

        inventoryForm?.let { inventoryForm ->
            formFieldNameMap.forEach { (english, chinese) ->

                val value = InventoryForm::class.java.getDeclaredField(english).let { field ->
                    field.isAccessible = true

                    val fieldValue = field.get(inventoryForm)
                    fieldValue?.toString() ?: ""
                }
                val formInputDataWidgetView = FormContentDataWidget(
                    activity = requireActivity(),
                    fieldName = chinese,
                    fieldEngName = english,
                    fieldContent = value
                )

                binding.linearData.addView(formInputDataWidgetView)
            }
        }
    }

    interface Listener {
        fun onChangeGoodsInfo(formItemJson: JSONObject, formGoodsDataWidget: FormGoodsDataWidget)
    }
}