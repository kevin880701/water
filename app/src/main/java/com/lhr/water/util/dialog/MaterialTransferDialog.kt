package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.lhr.water.R
import com.lhr.water.databinding.DialogMaterialTransferBinding
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.map.MapViewModel
import com.lhr.water.util.isInput
import com.lhr.water.util.showToast
import com.lhr.water.util.spinnerAdapter.DeptAdapter
import com.lhr.water.util.spinnerAdapter.RegionEntityAdapter
import com.lhr.water.util.spinnerAdapter.StorageEntityAdapter
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MaterialTransferDialog(
    var storageEntity: StorageEntity,
    var storageRecordEntity: StorageRecordEntity,
) : DialogFragment(), View.OnClickListener {

    private var _binding: DialogMaterialTransferBinding? = null
    private val binding get() = _binding!!
    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: MapViewModel by viewModels { viewModelFactory }

    private var dialog: AlertDialog? = null

    private lateinit var regionAdapter: RegionEntityAdapter
    private lateinit var deptAdapter: DeptAdapter
    private lateinit var storageEntityAdapter: StorageEntityAdapter
    private var allRegionList = ArrayList<RegionEntity>()
    private var regionSpinnerList = ArrayList<RegionEntity>()
    private var deptSpinnerList = ArrayList<RegionEntity>()
    private var storageSpinnerList = ArrayList<StorageEntity>()

    var selectRegion = MutableLiveData<RegionEntity>()
    var selectDept = MutableLiveData<RegionEntity>()
    var selectStorage = MutableLiveData<StorageEntity>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogMaterialTransferBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        binding.widgetTitleBar.imageCancel.setOnClickListener(View.OnClickListener {
            this.dismiss()
        })

        initView()
        bindViewModel()
        builder.setView(binding.root)

        dialog = builder.create()
        return builder.create()
    }

    private fun bindViewModel() {
        selectRegion.observe(this) { selectRegion ->
            deptSpinnerList = viewModel.getDeptSpinnerList(selectRegion.regionNumber, allRegionList)
            deptAdapter = DeptAdapter(requireContext(), deptSpinnerList)
            binding.spinnerDept.adapter = deptAdapter
        }

        selectDept.observe(this) { selectDept ->
            storageSpinnerList = viewModel.getStorageSpinnerList(selectDept.deptNumber, selectDept.mapSeq)
            storageEntityAdapter = StorageEntityAdapter(requireContext(), storageSpinnerList)
            binding.spinnerStorage.adapter = storageEntityAdapter
        }
    }

    fun initView() {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.goods_transfer)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE
        binding.textrMaterial.text = storageRecordEntity.materialName
        binding.editQuantity.setText(storageRecordEntity.quantity)
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
                selectRegion.postValue(regionSpinnerList[position])
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
                selectDept.postValue(deptSpinnerList[position])
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
                    selectStorage.postValue(storageSpinnerList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // 在沒有選中項的情況下觸發
                }
            }
        binding.buttonConfirm.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
                if (binding.spinnerStorage.selectedItem == null) {
                    showToast(requireContext(), "儲櫃未選擇")
                } else {
                    // 獲取輸入的數量並轉換為 Int 類型
                    val inputQuantity = binding.editQuantity.text.toString().toIntOrNull()
                    if (inputQuantity == null || inputQuantity < 0) {
                        // 如果輸入的數量為空或小於0，提示用戶
                        showToast(requireContext(), "數量不能小於0")
                    } else if (inputQuantity > storageRecordEntity.quantity.toInt()) {
                        // 如果輸入的數量大於允許的最大值
                        showToast(requireContext(), "數量不能超過 ${storageRecordEntity.quantity}")
                    } else {
                        val currentTime = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")
                        val formattedDate = currentTime.format(formatter)
                        val outputRecord = StorageRecordEntity(
                            storageId = storageEntity.storageId,
                            formType = "7",
                            formNumber = storageRecordEntity.formNumber,
                            materialName = storageRecordEntity.materialName,
                            materialNumber = storageRecordEntity.materialNumber,
                            materialStatus = "3",
                            userId = viewModel.userRepository.userInfo.value!!.userId,
                            quantity = inputQuantity.toString(),
                            recordDate = formattedDate,
                            storageArrivalId = storageRecordEntity.storageArrivalId,
                            itemId = storageRecordEntity.itemId
                        )
                        val inputRecord = StorageRecordEntity(
                            storageId = selectStorage.value!!.storageId,
                            formType = "7",
                            formNumber = storageRecordEntity.formNumber,
                            materialName = storageRecordEntity.materialName,
                            materialNumber = storageRecordEntity.materialNumber,
                            materialStatus = "2",
                            userId = viewModel.userRepository.userInfo.value!!.userId,
                            quantity = inputQuantity.toString(),
                            recordDate = formattedDate,
                            storageArrivalId = storageRecordEntity.storageArrivalId,
                            itemId = storageRecordEntity.itemId
                        )
                        SqlDatabase.getInstance().getStorageRecordDao().insertStorageRecordEntities(
                            listOf(outputRecord)
                        )
                        SqlDatabase.getInstance().getStorageRecordDao().insertStorageRecordEntities(
                            listOf(inputRecord)
                        )
                        viewModel.formRepository.loadSqlData()
                        this.dismiss()
                    }
                }
            }

            R.id.imageCancel -> {
                this.dismiss()
            }
        }
    }
}