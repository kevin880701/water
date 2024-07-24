package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.lhr.water.R
import com.lhr.water.data.form.BaseItem
import com.lhr.water.databinding.DialogInputBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.form.dealMaterial.DealMaterialViewModel
import com.lhr.water.util.isInput
import com.lhr.water.util.showToast
import com.lhr.water.util.spinnerAdapter.DeptAdapter
import com.lhr.water.util.spinnerAdapter.RegionEntityAdapter
import com.lhr.water.util.spinnerAdapter.StorageEntityAdapter

class DealMaterialDialog(
    var formEntity: FormEntity,
    itemDetail: BaseItem,
    maxQuantity: String
) : DialogFragment(), View.OnClickListener {

    private var dialog: AlertDialog? = null
    private var itemDetail = itemDetail
    private var _binding: DialogInputBinding? = null
    private val binding get() = _binding!!

    private lateinit var regionAdapter: RegionEntityAdapter
    private lateinit var deptAdapter: DeptAdapter
    private lateinit var storageEntityAdapter: StorageEntityAdapter
    private var maxQuantity = maxQuantity
    private var allRegionList = ArrayList<RegionEntity>()
    private var regionSpinnerList = ArrayList<RegionEntity>()
    private var deptSpinnerList = ArrayList<RegionEntity>()
    private var storageSpinnerList = ArrayList<StorageEntity>()

    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: DealMaterialViewModel by viewModels { viewModelFactory }

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

        binding.editQuantity.text = Editable.Factory.getInstance().newEditable(maxQuantity)

        // 取得區域列表
        allRegionList = viewModel.getInputRegionList()

        if (allRegionList.size == 0) {
            binding.textNoData.visibility = View.VISIBLE
        } else {
            binding.textNoData.visibility = View.INVISIBLE
        }
        regionSpinnerList = allRegionList.distinctBy { it.regionNumber } as ArrayList<RegionEntity>

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

        binding.buttonConfirm.setOnClickListener(this)
        binding.widgetTitleBar.imageCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
                if (binding.spinnerStorage.selectedItem == null) {
                    showToast(requireContext(), "儲櫃未選擇")
                } else {
                    if(formEntity.reportTitle == "材料調撥單"){
                        if(binding.editQuantity.text.toString().toInt() > maxQuantity.toInt()){
                            showToast(requireContext(), "數量不可大於剩餘數量")
                        }else{
                            viewModel.inputInTempGoods(
                                formEntity,
                                itemDetail,
                                viewModel.selectStorage.value!!,
                                binding.editQuantity.text.toString(),
                                isInput(formEntity)
                            )
                            this.dismiss()
                        }
                    }else{
                        viewModel.inputInTempGoods(
                            formEntity,
                            itemDetail,
                            viewModel.selectStorage.value!!,
                            binding.editQuantity.text.toString(),
                            isInput(formEntity)
                        )
                        this.dismiss()
                    }
                }
            }

            R.id.imageCancel -> {
                this.dismiss()
            }

//            R.id.imageSubtract -> {
//                // 減少數量，但不小於 1
//                binding.textQuantity.text =
//                    maxOf(0, binding.textQuantity.text.toString().toInt() - 1).toString()
//            }
//
//            R.id.imageAdd -> {
//                // 增加數量，但不大於 maxQuantity
//                binding.textQuantity.text = minOf(
//                    maxQuantity.toInt(),
//                    binding.textQuantity.text.toString().toInt() + 1
//                ).toString()
//            }
        }
    }
}