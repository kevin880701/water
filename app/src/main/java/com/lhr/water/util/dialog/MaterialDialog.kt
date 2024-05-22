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
import com.lhr.water.room.StorageRecordEntity
import org.json.JSONObject

class MaterialDialog(
    var storageRecordEntity: StorageRecordEntity,
    var listener: Listener
) : DialogFragment() {

    private var dialog: AlertDialog? = null

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

    }


    interface Listener {
        fun onGoodsDialogConfirm(formItemJson: JSONObject)
    }
}