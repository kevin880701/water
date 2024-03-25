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
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.DialogInputBinding
import com.lhr.water.room.MapEntity
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.adapter.SpinnerAdapter
import com.lhr.water.util.showToast

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
    private var regionList = ArrayList<RegionEntity>()
    private var mapList = ArrayList<MapEntity>()
    private var storageList = ArrayList<StorageEntity>()

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

        materialName = waitDealGoodsData.itemDetail.materialName.toString()
        materialNumber = waitDealGoodsData.itemDetail.materialNumber.toString()
        binding.textQuantity.text = maxQuantity

        if (isInput) {
            storageList = viewModel.regionRepository.storageEntities.value!!
            regionList = viewModel.getInputGoodsRegion(storageList)
            mapList = viewModel.getInputGoodsMap(storageList)
            storageList = viewModel.getInputGoodsStorage(storageList)
        } else {
            var storageContentList = viewModel.formRepository.storageGoods.value?.filter { entity ->
                entity.materialName == waitDealGoodsData.itemDetail.materialName &&
                        entity.materialNumber == waitDealGoodsData.itemDetail.materialNumber &&
                        entity.materialSpec == waitDealGoodsData.itemDetail.materialSpec &&
                        entity.materialUnit == waitDealGoodsData.itemDetail.materialUnit
            } as ArrayList
            regionList = viewModel.getOutputGoodsRegion(storageContentList)
            mapList = viewModel.getOutputGoodsMap(storageContentList)
            storageList = viewModel.getOutputGoodsStorage(storageContentList)
        }
        if (storageList.size == 0) {
            binding.textNoData.visibility = View.VISIBLE
        } else {
            binding.textNoData.visibility = View.INVISIBLE
        }

        initSpinner(binding.spinnerRegion, viewModel.getRegionNameList(regionList))

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
                initSpinner(binding.spinnerMap, viewModel.getMapNameList(regionName, mapList))

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
                        viewModel.getStorageNameList(regionName, mapName, storageList)
                            .map { it.storageName })
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
                    if (!isInput) {
                        var goodsStoreInformation = viewModel.getOutputGoodsStorageInformation(
                            waitDealGoodsData.itemDetail.materialName.toString(),
                            waitDealGoodsData.itemDetail.materialNumber.toString()
                        )
                        var materialQuantity = viewModel.regionRepository.getMaterialQuantity(
                            regionName,
                            mapName,
                            storageName,
                            materialNumber,
                            goodsStoreInformation
                        ).toInt()
                        maxQuantity =
                            kotlin.math.min(materialQuantity, maxQuantity.toInt()).toString()
                        binding.textQuantity.text = maxQuantity
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
                if (binding.spinnerStorage.selectedItem == null) {
                    showToast(requireContext(), "儲櫃未選擇")
                } else {
                    if (isInput) {
                        viewModel.inputInTempGoods(
                            waitDealGoodsData,
                            binding.spinnerRegion.selectedItem.toString(),
                            binding.spinnerMap.selectedItem.toString(),
                            binding.spinnerStorage.selectedItem.toString(),
                            binding.textQuantity.text.toString()
                        )
                    } else {
                        viewModel.outputInTempGoods(
                            waitDealGoodsData,
                            binding.spinnerRegion.selectedItem.toString(),
                            binding.spinnerMap.selectedItem.toString(),
                            binding.spinnerStorage.selectedItem.toString(),
                            binding.textQuantity.text.toString()
                        )
                    }
                    this.dismiss()
                }
            }

            R.id.imageCancel -> {
                this.dismiss()
            }

            R.id.imageSubtract -> {
                // 減少數量，但不小於 1
                binding.textQuantity.text =
                    maxOf(0, binding.textQuantity.text.toString().toInt() - 1).toString()
            }

            R.id.imageAdd -> {
                // 增加數量，但不大於 maxQuantity
                binding.textQuantity.text = minOf(
                    maxQuantity.toInt(),
                    binding.textQuantity.text.toString().toInt() + 1
                ).toString()
            }
        }
    }
}