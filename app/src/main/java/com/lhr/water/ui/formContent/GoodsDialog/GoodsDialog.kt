package com.lhr.water.ui.formContent.GoodsDialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.lhr.water.R
import com.lhr.water.data.FormGoodInfo
import com.lhr.water.databinding.DialogFormGoodsBinding
import com.lhr.water.util.widget.FormGoodsDataWidget
import com.lhr.water.util.widget.FormInputDataWidget

class GoodsDialog(
    isAdd: Boolean,
    listener: Listener,
    var formGoodInfo: FormGoodInfo? = null,
    var formGoodsDataWidget: FormGoodsDataWidget? = null
) : DialogFragment() {

    private var dialog: AlertDialog? = null
    private var listener = listener
    private var isAdd = isAdd

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogFormGoodsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.dialog_form_goods,
            null,
            false
        )
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        binding.imageConfirm.setOnClickListener {
            var list = ArrayList<String?>()
            for (i in 0 until binding.linearData.childCount) {
                val linearLayoutItem = binding.linearData.getChildAt(i)  // 假设您的外层容器是 LinearLayout
                val editText = linearLayoutItem.findViewById<EditText>(R.id.editDataContent)
                val editTextValue = editText.text.toString()
                list.add(editTextValue)
            }
            val formGoodInfo = FormGoodInfo(
                list[0],
                list[1],
                list[2],
                list[3],
                list[4],
                list[5],
                list[6],
                list[7],
                list[8]
            )
            if (isAdd) {
                listener.onAddGoods(formGoodInfo)
            } else {
                    formGoodsDataWidget?.let { it ->
                        listener.onChangeGoodsInfo(formGoodInfo,
                            it
                        )
                    }
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
            activity?.resources?.getStringArray(R.array.delivery_goods_form)?.toList() as ArrayList<String>

        formGoodInfo?.let { formGoodInfo ->
            val dataContentList = listOf(
                formGoodInfo.goodsNumber,
                formGoodInfo.goodsName,
                formGoodInfo.specification,
                formGoodInfo.unit,
                formGoodInfo.allocationQuantity,
                formGoodInfo.allocationSequenceNumber,
                formGoodInfo.actualAllocationQuantity,
                formGoodInfo.actualReceiptQuantity,
                formGoodInfo.comments
            )
                dataNameList.forEachIndexed { index, dataName ->
                    val formInputDataWidgetView = FormInputDataWidget(
                        requireActivity(),
                        dataName,
                        false,
                        dataContent = dataContentList[index]
                    )

                    binding.linearData.addView(formInputDataWidgetView)
                }

        } ?: run {
            dataNameList.forEach { dataName ->
                val formInputDataWidgetView = FormInputDataWidget(
                    requireActivity(),
                    dataName,
                    false
                )

                binding.linearData.addView(formInputDataWidgetView)
            }
        }
    }

    interface Listener {
        fun onAddGoods(formGoodInfo: FormGoodInfo)
        fun onChangeGoodsInfo(formGoodInfo: FormGoodInfo, formGoodsDataWidget: FormGoodsDataWidget)
    }
}

//
//class GoodsDialog {
//
//    private var _binding: DialogEditBinding? = null
//    private var binding get() = _binding!!
//    private var dialog: AlertDialog? = null
//
//    interface OnEditListener {
//        fun onEdit(toString: String)
//    }
//
//    constructor(activity: AppCompatActivity, title: String?, onClickListener: OnEditListener?){
//        binding = DataBindingUtil.inflate(
//        LayoutInflater.from(activity), R.layout.dialog_edit, null, false)
//        initGoodsData(activity)
//    }
//    fun showEditDialog(
//        activity: AppCompatActivity, title: String?, onClickListener: OnEditListener?
//    ) {
//        activity.runOnUiThread {
//            val builder = AlertDialog.Builder(activity)
//            builder.setCancelable(false)
//
//            binding.imageConfirm.setOnClickListener {
//                dialog!!.dismiss()
////                onClickListener?.onEdit(binding.content.text.toString())
//            }
//            binding.imageClose.setOnClickListener(View.OnClickListener { dialog!!.dismiss() })
//            builder.setView(binding.root)
//            dialog = builder.create()
//            dialog?.setCancelable(false)
//            Objects.requireNonNull(dialog?.window)
//                ?.setBackgroundDrawableResource(android.R.color.transparent)
//            dialog?.show()
//            val lp = WindowManager.LayoutParams()
//            val dm = DisplayMetrics()
//            activity.windowManager.defaultDisplay.getMetrics(dm)
//            lp.copyFrom(dialog?.window!!.attributes)
//            lp.width = (dm.widthPixels * 0.7).toInt()
//            dialog?.window!!.attributes = lp
//        }
//    }
//
//
//    fun initGoodsData(activity: AppCompatActivity) {
//        var list = activity.resources.getStringArray(R.array.goods_form).toList() as ArrayList<String>
//
//        list.forEach { dataName ->
//            val formInputDataView = FormInputData(activity, dataName, false)
//
//            binding.linearData.addView(formInputDataView)
//        }
//    }
//
//}