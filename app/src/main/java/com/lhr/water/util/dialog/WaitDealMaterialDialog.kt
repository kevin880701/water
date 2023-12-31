package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.data.RegionInformation
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.DialogInputBinding
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.adapter.SpinnerAdapter
import org.json.JSONObject

class WaitDealMaterialDialog(
    waitDealGoodsData: WaitDealGoodsData,
    maxQuantity: String,
    val isInput: Boolean
) : DialogFragment(), View.OnClickListener {

    private var dialog: AlertDialog? = null
    private var waitDealGoodsData = waitDealGoodsData
    private var _binding: DialogInputBinding? = null
    private val binding get() = _binding!!
    private var regionName = ""
    private var mapName = ""
    private var storageName = ""
    private var maxQuantity = maxQuantity
    private var materialName = ""
    private var materialNumber = ""
    private var spinnerList = ArrayList<RegionInformation>()

    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogInputBinding.inflate(layoutInflater)
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

        materialName = waitDealGoodsData.itemInformation.getString("materialName")
        materialNumber = waitDealGoodsData.itemInformation.getString("materialNumber")
        binding.textQuantity.text = maxQuantity

        spinnerList = if (isInput){
            viewModel.regionRepository.storageInformationList
        }else{
            viewModel.getOutputGoodsWhere(waitDealGoodsData.itemInformation)
        }

        initSpinner(binding.spinnerRegion, viewModel.getRegionNameList(spinnerList))

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
                initSpinner(binding.spinnerMap, viewModel.getMapNameList(regionName, spinnerList))

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
                        viewModel.getStorageNameList(regionName, mapName, spinnerList).map { it.StorageName })
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
                    // 如果是出貨，需採儲櫃剩餘數量和出貨數量中較小的那個做為最大值
                    if(!isInput){
                        var goodsStoreInformation = viewModel.getOutputGoodsStorageInformation(waitDealGoodsData.itemInformation.optString("materialName"),waitDealGoodsData.itemInformation.optString("materialNumber"))
                        var materialQuantity = viewModel.regionRepository.getMaterialQuantity(regionName, mapName, viewModel.regionRepository.findStorageNum(regionName, mapName, storageName), materialNumber, goodsStoreInformation).toInt()
                        maxQuantity = kotlin.math.min(materialQuantity, maxQuantity.toInt()).toString()
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // 在沒有選中項的情況下觸發
                }
            }
        binding.imageSubtract.setOnClickListener(this)
        binding.imageAdd.setOnClickListener(this)
        binding.buttonConfirm.setOnClickListener(this)
        binding.widgetTitleBar.imageCancel.setOnClickListener(this)
    }

    private fun initSpinner(spinner: Spinner, spinnerData: ArrayList<String>) {
        val adapter =
            SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerData)
        spinner.adapter = adapter
    }

    interface Listener {
        fun onGoodsDialogConfirm(formItemJson: JSONObject)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
                // 先找出儲櫃代號，不可直接用儲櫃名稱，因為可能會被修改
                val storageNum = viewModel.regionRepository.findStorageNum(binding.spinnerRegion.selectedItem.toString(),
                    binding.spinnerMap.selectedItem.toString(),
                    binding.spinnerStorage.selectedItem.toString())
                if (isInput){
                    viewModel.inputInTempGoods(
                        waitDealGoodsData,
                        binding.spinnerRegion.selectedItem.toString(),
                        binding.spinnerMap.selectedItem.toString(),
                        storageNum,
                        binding.textQuantity.text.toString()
                    )
                }else{
                    viewModel.outputInTempGoods(
                        waitDealGoodsData,
                        binding.spinnerRegion.selectedItem.toString(),
                        binding.spinnerMap.selectedItem.toString(),
                        storageNum,
                        binding.textQuantity.text.toString()
                    )
                }
                this.dismiss()
            }

            R.id.imageCancel -> {
                this.dismiss()
            }

            R.id.imageSubtract -> {
                // 減少數量，但不小於 1
                binding.textQuantity.text = maxOf(1, binding.textQuantity.text.toString().toInt() - 1).toString()
            }

            R.id.imageAdd -> {
                // 增加數量，但不大於 maxQuantity
                binding.textQuantity.text = minOf(maxQuantity.toInt(), binding.textQuantity.text.toString().toInt() + 1).toString()
            }
        }
    }
}