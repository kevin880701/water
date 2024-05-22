package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.lhr.water.R
import com.lhr.water.databinding.DialogDefaultBinding
import com.lhr.water.util.widget.MaterialWidget
import org.json.JSONObject

class DefaultDialog(
    var title: String,
    var text: String,
    confirmClick: (() -> Unit)? = null,
    cancelClick: (() -> Unit)? = null
) : DialogFragment() {

    private var dialog: AlertDialog? = null

    private var confirmClick = confirmClick ?: { dismiss() }
    private var cancelClick = cancelClick ?: { dismiss() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogDefaultBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.dialog_default,
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

    fun initView(binding: DialogDefaultBinding) {
        binding.widgetTitleBar.textTitle.text = title
        binding.text.text = text
        binding.buttonConfirm.setOnClickListener(View.OnClickListener {
            confirmClick()
            this.dismiss()
        })

        binding.buttonCancel.setOnClickListener(View.OnClickListener {
            cancelClick
            this.dismiss()
        })
    }


    interface Listener {
        fun onGoodsDialogConfirm(formItemJson: JSONObject)
        fun onChangeGoodsInfo(formItemJson: JSONObject, formGoodsDataWidget: MaterialWidget)
    }
}