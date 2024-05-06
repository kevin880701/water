package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.data.Form
import com.lhr.water.data.ItemDetail
import com.lhr.water.databinding.DialogInputBinding
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.form.FormViewModel
import com.lhr.water.util.showToast
import com.lhr.water.util.spinnerAdapter.DeptAdapter
import com.lhr.water.util.spinnerAdapter.RegionEntityAdapter
import com.lhr.water.util.spinnerAdapter.StorageEntityAdapter

class WaitDealMaterialDialog(
    var form: Form,
    itemDetail: ItemDetail,
    maxQuantity: String,
    val isInput: Boolean
) : DialogFragment(), View.OnClickListener {

    private var dialog: AlertDialog? = null
    private var itemDetail = itemDetail
    private var _binding: DialogInputBinding? = null
    private val binding get() = _binding!!

    private lateinit var regionAdapter: RegionEntityAdapter
    private lateinit var deptAdapter: DeptAdapter
    private lateinit var storageEntityAdapter: StorageEntityAdapter
    private var mapName = ""
    private var maxQuantity = maxQuantity
    private var materialName = ""
    private var materialNumber = ""
    private var regionList = ArrayList<RegionEntity>()
    private var storageList = ArrayList<StorageEntity>()
    private var allRegionList = ArrayList<RegionEntity>()
    private var regionSpinnerList = ArrayList<RegionEntity>()
    private var deptSpinnerList = ArrayList<RegionEntity>()
    private var storageSpinnerList = ArrayList<StorageEntity>()

    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: FormViewModel by viewModels { viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogInputBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        initView()
        bindViewModel()
        builder.setView(binding.root)
        dialog = builder.create()
        return builder.create()
    }

    private fun bindViewModel() {
        viewModel.selectRegion.observe(this) { selectRegion ->
            deptSpinnerList = viewModel.getDeptSpinnerList(selectRegion.regionNumber, allRegionList)
            deptAdapter = DeptAdapter(requireContext(), deptSpinnerList)
            binding.spinnerDept.adapter = deptAdapter
        }

        viewModel.selectDept.observe(this) { selectDept ->
            storageSpinnerList = viewModel.getStorageSpinnerList(selectDept.deptNumber, selectDept.mapSeq)
            storageEntityAdapter = StorageEntityAdapter(requireContext(), storageSpinnerList)
            binding.spinnerStorage.adapter = storageEntityAdapter
        }
    }

    fun initView() {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.goods_information)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE

        materialName = itemDetail.materialName.toString()
        materialNumber = itemDetail.materialNumber.toString()
        binding.textQuantity.text = maxQuantity

        // 取得區域列表
        allRegionList = viewModel.getAllRegionList()
        regionSpinnerList = allRegionList.distinctBy { it.regionNumber } as ArrayList<RegionEntity>

        if (isInput) {
            storageList = viewModel.regionRepository.storageEntities
            regionList = viewModel.getInputGoodsRegion(storageList)
            storageList = viewModel.getInputGoodsStorage(storageList)
        } else {
//            var storageContentList = viewModel.formRepository.storageGoods.value?.filter { entity ->
//                entity.materialName == itemDetail.materialName &&
//                        entity.materialNumber == itemDetail.materialNumber &&
//                        entity.materialSpec == itemDetail.materialSpec &&
//                        entity.materialUnit == itemDetail.materialUnit
//            } as ArrayList
//            regionList = viewModel.getOutputGoodsRegion(storageContentList)
//            storageList = viewModel.getOutputGoodsStorage(storageContentList)
        }
        if (storageList.size == 0) {
            binding.textNoData.visibility = View.VISIBLE
        } else {
            binding.textNoData.visibility = View.INVISIBLE
        }

        // 設定區域Spinner
        regionAdapter = RegionEntityAdapter(requireContext(), regionSpinnerList)
        binding.spinnerRegion.adapter = regionAdapter
        // 設定 Spinner 的選擇監聽器
        binding.spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 通過 position 獲取當前選定項的文字
                viewModel.selectRegion.postValue(regionSpinnerList[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 在沒有選中項的情況下觸發
            }
        }


        // 設定部門Spinner
        binding.spinnerDept.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.selectDept.postValue(deptSpinnerList[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 在沒有選中項的情況下觸發
            }
        }

        // 設定儲櫃Spinner
        binding.spinnerStorage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // 通過 position 獲取當前選定項的文字
                    // 如果是出貨，需採儲櫃剩餘數量和出貨數量中較小的那個做為最大值
//                    if (!isInput) {
//                        var goodsStoreInformation = viewModel.getOutputGoodsStorageInformation(
//                            waitDealGoodsData.itemDetail.materialName.toString(),
//                            waitDealGoodsData.itemDetail.materialNumber.toString()
//                        )
//                        var materialQuantity = viewModel.regionRepository.getMaterialQuantity(
//                            regionName,
//                            mapName,
//                            storageName,
//                            materialNumber,
//                            goodsStoreInformation
//                        ).toInt()
//                        maxQuantity =
//                            kotlin.math.min(materialQuantity, maxQuantity.toInt()).toString()
//                        binding.textQuantity.text = maxQuantity
//                    }
                    viewModel.selectStorage.postValue(storageSpinnerList[position])
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
                if (binding.spinnerStorage.selectedItem == null) {
                    showToast(requireContext(), "儲櫃未選擇")
                } else {
                    if (isInput) {
                        viewModel.inputInTempGoods(
                            form,
                            itemDetail,
                            viewModel.selectStorage.value!!,
                            binding.textQuantity.text.toString()
                        )
                    } else {
                        viewModel.outputInTempGoods(
                            form,
                            itemDetail,
                            viewModel.selectStorage.value!!,
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