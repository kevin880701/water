package com.lhr.water.util.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.WidgetBottomStorageContentBinding
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.ui.map.MapViewModel
import com.lhr.water.util.adapter.StorageContentAdapter
import com.lhr.water.util.dialog.GoodsDialog
import org.json.JSONObject

class StorageContentBottom(
    var activity: MapActivity,
    var storageEntity: StorageEntity,
) : RelativeLayout(activity), View.OnClickListener, StorageContentAdapter.Listener, GoodsDialog.Listener {

    val viewModel: MapViewModel by activity.viewModels {
        (activity.applicationContext as APP).appContainer.viewModelFactory
    }
    private var binding: WidgetBottomStorageContentBinding
    private lateinit var storageContentAdapter: StorageContentAdapter
    val formRepository: FormRepository by lazy { FormRepository.getInstance(activity) }

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_bottom_storage_content, this, true
        )

        activity.onBackPressedDispatcher.addCallback(
            activity, // LifecycleOwner
            activity.onBackPressedCallback
        )

        initView()
        bindViewModel()
    }

    fun initView() {
        initRecyclerView()

        binding.root.setOnClickListener(this)
        binding.constraintBack.setOnClickListener(this)
    }


    private fun bindViewModel() {
    }

    private fun initRecyclerView() {
        storageContentAdapter = StorageContentAdapter(this, activity)
        storageContentAdapter.submitList(
            viewModel.getStorageContentList(storageEntity.storageId)
        )

        binding.recyclerGoods.adapter = storageContentAdapter
        binding.recyclerGoods.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.constraintBack -> {
                activity.onBackPressedCallback.handleOnBackPressed()
            }

            R.id.imageBack -> {
                activity.onBackPressedCallback.handleOnBackPressed()
            }
        }
    }

    /**
     * 點擊列顯示對應貨物資訊的Dialog
     * @param storageRecordEntity 貨物資訊
     */
    override fun onItemClick(storageRecordEntity: StorageRecordEntity) {
        val goodFieldNameList = resources.getStringArray(R.array.storage_Good_field_name)
            .toList() as ArrayList<String>
        val goodFieldNameEngList =
            resources.getStringArray(R.array.storage_Good_field_name_eng)
                .toList() as ArrayList<String>
        val goodContent = ArrayList<String>()
//        val goodJsonObject = jsonStringToJson(storageContentEntity.itemInformation)
//        goodFieldNameEngList.forEach { key ->
//            if (goodJsonObject.has(key)) {
//                val value = goodJsonObject.getString(key)
//                goodContent.add(value)
//            } else {
//                // Handle the case where the key is not present in the JSON object
//                // You can choose to add a default value or take any other action
//                goodContent.add("Key $key not found")
//            }
//        }
//        val goodsDialog = GoodsDialog(
//            isAdd = true,
//            formItemFieldNameList = goodFieldNameList,
//            formItemFieldNameEngList = goodFieldNameEngList,
//            listener = this,
//            formItemFieldContentList = goodContent
//        )
//        goodsDialog.show(activity.supportFragmentManager, "GoodsDialog")
    }

    override fun onGoodsDialogConfirm(formItemJson: JSONObject) {

    }

    override fun onChangeGoodsInfo(
        formItemJson: JSONObject,
        formGoodsDataWidget: FormGoodsDataWidget
    ) {
    }
}