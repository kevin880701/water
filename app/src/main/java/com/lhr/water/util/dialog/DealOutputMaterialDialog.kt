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
import com.lhr.water.databinding.DialogOutputBinding
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.form.dealMaterial.DealMaterialViewModel
import com.lhr.water.util.isInput
import com.lhr.water.util.showToast
import com.lhr.water.util.spinnerAdapter.DeptAdapter
import com.lhr.water.util.spinnerAdapter.InputTimeEntityAdapter
import com.lhr.water.util.spinnerAdapter.RegionEntityAdapter
import com.lhr.water.util.spinnerAdapter.StorageEntityAdapter

class DealOutputMaterialDialog(
    var form: Form,
    itemDetail: ItemDetail,
    var needOutputQuantity: String
) : DialogFragment(), View.OnClickListener {

    private var dialog: AlertDialog? = null
    private var itemDetail = itemDetail
    private var _binding: DialogOutputBinding? = null
    private val binding get() = _binding!!

    private lateinit var regionAdapter: RegionEntityAdapter
    private lateinit var deptAdapter: DeptAdapter
    private lateinit var storageEntityAdapter: StorageEntityAdapter
    private lateinit var inputTimeAdapter: InputTimeEntityAdapter
    private var allRegionList = ArrayList<RegionEntity>()
    private var regionSpinnerList = ArrayList<RegionEntity>()
    private var deptSpinnerList = ArrayList<RegionEntity>()
    private var storageSpinnerList = ArrayList<StorageEntity>()
    private var inputTimeSpinnerList = ArrayList<String>()
    private var specifiedMaterialStorageRecordEntities = ArrayList<StorageRecordEntity>()
    private var maxQuantity = needOutputQuantity

    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: DealMaterialViewModel by viewModels { viewModelFactory }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogOutputBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        // 先抓指定要出庫貨物在那些儲櫃，有多少數量
        specifiedMaterialStorageRecordEntities = viewModel.getSpecifiedMaterialStorageRecordEntities(itemDetail.materialNumber!!)
        println("=============================================================")
        println("specifiedMaterialStorageRecordEntities：${specifiedMaterialStorageRecordEntities.size}")
        println("=============================================================")
        // 取得區域列表
        allRegionList = viewModel.getOutputRegionList(specifiedMaterialStorageRecordEntities)
        regionSpinnerList = allRegionList.distinctBy { it.regionNumber } as ArrayList<RegionEntity>

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
            println("******************************************************************************@@@@@@@")
            storageSpinnerList = viewModel.getOutputStorageSpinnerList(selectDept.deptNumber, selectDept.mapSeq, specifiedMaterialStorageRecordEntities)
            storageEntityAdapter = StorageEntityAdapter(requireContext(), storageSpinnerList)
            binding.spinnerStorage.adapter = storageEntityAdapter
        }

        viewModel.selectStorage.observe(this) { selectStorage ->
            inputTimeSpinnerList = viewModel.getOutputTimeSpinnerList(selectStorage.storageId, specifiedMaterialStorageRecordEntities)
            inputTimeAdapter = InputTimeEntityAdapter(requireContext(), inputTimeSpinnerList)
            binding.spinnerInputTime.adapter = inputTimeAdapter
        }

        viewModel.selectInputTime.observe(this) { selectInputTime ->
            maxQuantity = viewModel.getOutputMaxQuantity(needOutputQuantity.toInt(), viewModel.selectStorage.value!!.storageId, selectInputTime, specifiedMaterialStorageRecordEntities).toString()
            viewModel.currentQuantity.postValue(maxQuantity)
        }

        viewModel.currentQuantity.observe(this) { currentQuantity ->
            binding.textQuantity.text = currentQuantity
        }
    }

    fun initView() {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.goods_information)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE

        if (allRegionList.size == 0) {
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
                    viewModel.selectStorage.postValue(storageSpinnerList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // 在沒有選中項的情況下觸發
                }
            }


        // 設定材料入庫時間Spinner
        binding.spinnerInputTime.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectInputTime.postValue(inputTimeSpinnerList[position])
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
                    println("viewModel.selectInputTime.value：${viewModel.selectInputTime.value}");
                    viewModel.inputInTempGoods(
                        form,
                        itemDetail,
                        viewModel.selectStorage.value!!,
                        binding.textQuantity.text.toString(),
                        isInput(form),
                        viewModel.selectInputTime.value)
                    this.dismiss()
                }
            }

            R.id.imageCancel -> {
                this.dismiss()
            }

            R.id.imageSubtract -> {
                // 減少數量，但不小於 1
                viewModel.currentQuantity.postValue(maxOf(1, binding.textQuantity.text.toString().toInt() - 1).toString())
            }

            R.id.imageAdd -> {
                // 增加數量，但不大於 maxQuantity
                viewModel.currentQuantity.postValue(minOf(
                    maxQuantity.toInt(),
                    binding.textQuantity.text.toString().toInt() + 1
                ).toString())
            }
        }
    }
}