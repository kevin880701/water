package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.ActivityLoginBinding
import com.lhr.water.databinding.DialogInputGoodsBinding
import com.lhr.water.model.LoginData
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.goods.GoodsViewModel
import com.lhr.water.ui.main.MainActivity
import com.lhr.water.util.adapter.SpinnerAdapter
import org.json.JSONObject
import timber.log.Timber

class InputGoodsDialog(
    waitDealGoodsData: WaitDealGoodsData,
    listener: Listener
) : DialogFragment(), View.OnClickListener {

    private var dialog: AlertDialog? = null
    private var waitDealGoodsData = waitDealGoodsData
    private var listener = listener
    private var _binding: DialogInputGoodsBinding? = null
    private val binding get() = _binding!!
    var regionName = ""
    var mapName = ""
    var storageName = ""

    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: GoodsViewModel by viewModels { viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogInputGoodsBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        initView()
        builder.setView(binding.root)
        dialog = builder.create()
        return builder.create()
    }

    fun initView() {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.goods_information)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE
        initSpinner(binding.spinnerRegion, viewModel.getRegionNameList())
        initGoodsData(binding)

        // 設定 Spinner 的選擇監聽器
        binding.spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 通過 position 獲取當前選定項的文字
                regionName = parent?.getItemAtPosition(position).toString()
                initSpinner(binding.spinnerMap, viewModel.getMapNameList(regionName))

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 在沒有選中項的情況下觸發
            }
        }

        // 設定 Spinner 的選擇監聽器
        binding.spinnerMap.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 通過 position 獲取當前選定項的文字
                mapName = parent?.getItemAtPosition(position).toString()
                initSpinner(
                    binding.spinnerStorage,
                    ArrayList(
                        viewModel.getStorageNameList(regionName, mapName).map { it.StorageName })
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 在沒有選中項的情況下觸發
            }
        }

        // 設定 Spinner 的選擇監聽器
        binding.spinnerStorage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // 通過 position 獲取當前選定項的文字
                    storageName = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // 在沒有選中項的情況下觸發
                }
            }
        binding.buttonConfirm.setOnClickListener(this)
        binding.widgetTitleBar.imageCancel.setOnClickListener(this)
    }

    private fun initSpinner(spinner: Spinner, spinnerData: ArrayList<String>) {
        val adapter =
            SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerData)
        spinner.adapter = adapter
    }

    fun initGoodsData(binding: DialogInputGoodsBinding) {
        var dataNameList =
            activity?.resources?.getStringArray(R.array.delivery_Item_field_name)
                ?.toList() as ArrayList<String>
    }

    interface Listener {
        fun onGoodsDialogConfirm(formItemJson: JSONObject)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
                viewModel.inputGoods(
                    waitDealGoodsData,
                    binding.spinnerRegion.selectedItem.toString(),
                    binding.spinnerMap.selectedItem.toString(),
                    binding.spinnerStorage.selectedItem.toString()
                )
                this.dismiss()
            }

            R.id.imageCancel -> {
                this.dismiss()
            }
        }
    }
}