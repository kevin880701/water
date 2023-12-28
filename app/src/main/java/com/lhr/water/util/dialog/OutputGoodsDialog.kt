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
import com.lhr.water.databinding.DialogOutputGoodBinding
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.goods.GoodsViewModel
import com.lhr.water.util.adapter.SpinnerAdapter
import com.lhr.water.util.adapter.WaitOutputGoodsStorageAdapter
import org.json.JSONObject
import timber.log.Timber

class OutputGoodsDialog(
    waitDealGoodsData: WaitDealGoodsData,
    listener: Listener
) : DialogFragment(), View.OnClickListener, WaitOutputGoodsStorageAdapter.Listener {

    private var dialog: AlertDialog? = null
    private var waitDealGoodsData = waitDealGoodsData
    private var listener = listener
    private var _binding: DialogOutputGoodBinding? = null
    private val binding get() = _binding!!
    var materialName = ""
    var materialNumber = ""
    var maxQuantity = 0
    private lateinit var waitOutputGoodsStorageAdapter: WaitOutputGoodsStorageAdapter
    private lateinit var goodsStoreLocation: ArrayList<RegionInformation>  //貨物在那些儲櫃裡
    private lateinit var goodsStoreInformation: ArrayList<StorageContentEntity>  //儲櫃裡的詳細資訊

    var regionName = ""
    var mapName = ""
    var storageName = ""
    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: GoodsViewModel by viewModels { viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogOutputGoodBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)
        goodsStoreLocation = viewModel.getOutputGoodsWhere(waitDealGoodsData.itemInformation.optString("materialName"),waitDealGoodsData.itemInformation.optString("materialNumber"))
        goodsStoreInformation = viewModel.getOutputGoodsStorageInformation(waitDealGoodsData.itemInformation.optString("materialName"),waitDealGoodsData.itemInformation.optString("materialNumber"))
        initView()
        materialName = waitDealGoodsData.itemInformation.getString("materialName")
        materialNumber = waitDealGoodsData.itemInformation.getString("materialNumber")
        builder.setView(binding.root)
        dialog = builder.create()
        return builder.create()
    }

    fun initView() {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.goods_information)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE

        initSpinner(binding.spinnerRegion, viewModel.regionRepository.getRegionNameList(goodsStoreLocation))

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
                initSpinner(binding.spinnerMap, viewModel.getMapNameList(regionName, goodsStoreLocation))

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
                        viewModel.getStorageNameList(regionName, mapName, goodsStoreLocation).map { it.StorageName })
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
                    maxQuantity = viewModel.regionRepository.getMaterialQuantity(regionName, mapName, viewModel.regionRepository.findStorageNum(regionName, mapName, storageName), materialNumber, goodsStoreInformation).toInt()
                    binding.textQuantity.text = maxQuantity.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // 在沒有選中項的情況下觸發
                }
            }

        initRecyclerView()

        binding.imageSubtract.setOnClickListener(this)
        binding.imageAdd.setOnClickListener(this)
        binding.buttonConfirm.setOnClickListener(this)
        binding.widgetTitleBar.imageCancel.setOnClickListener(this)
    }

    private fun initRecyclerView() {
//        waitOutputGoodsStorageAdapter = WaitOutputGoodsStorageAdapter(this)
//        waitOutputGoodsStorageAdapter.submitList(viewModel.getOutputGoodsWhere(waitDealGoodsData.itemInformation.optString("materialName"),waitDealGoodsData.itemInformation.optString("materialNumber")))
//        binding.recyclerGoods.layoutManager = LinearLayoutManager(activity)
//        binding.recyclerGoods.adapter = waitOutputGoodsStorageAdapter
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
                this.dismiss()
            }

            R.id.imageCancel -> {
                this.dismiss()
            }

            R.id.imageSubtract -> {
                // 減少數量，但不小於 0
                binding.textQuantity.text = maxOf(0, binding.textQuantity.text.toString().toInt() - 1).toString()
            }

            R.id.imageAdd -> {
                // 增加數量，但不大於 maxQuantity
                binding.textQuantity.text = minOf(maxQuantity, binding.textQuantity.text.toString().toInt() + 1).toString()
            }
        }
    }

    override fun onItemClick(item: StorageContentEntity) {

    }
}