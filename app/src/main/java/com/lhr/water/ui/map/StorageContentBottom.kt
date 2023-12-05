package com.lhr.water.ui.map

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.data.StorageDetail
import com.lhr.water.databinding.WidgetBottomStorageContentBinding
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.util.adapter.StorageContentAdapter
import com.lhr.water.util.dialog.GoodsDialog
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.widget.FormGoodsDataWidget
import org.json.JSONObject
import timber.log.Timber

class StorageContentBottom(
    listener: Listener,
    activity: MapActivity,
    storageDetail: StorageDetail,
    map: String,
    region: String
) : RelativeLayout(activity), View.OnClickListener, StorageContentAdapter.Listener, GoodsDialog.Listener {

    val viewModel: MapViewModel by activity.viewModels {
        (activity.applicationContext as APP).appContainer.viewModelFactory
    }
    private var binding: WidgetBottomStorageContentBinding
    private val listener: Listener
    private val activity: MapActivity
    private lateinit var storageContentAdapter: StorageContentAdapter
    private val storageDetail: StorageDetail
    private val map: String
    private val region: String
    val formRepository: FormRepository by lazy { FormRepository.getInstance(activity) }

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_bottom_storage_content, this, true
        )
        this@StorageContentBottom.listener = listener
        this@StorageContentBottom.activity = activity
        this@StorageContentBottom.storageDetail = storageDetail
        this@StorageContentBottom.map = map
        this@StorageContentBottom.region = region

        activity.onBackPressedDispatcher.addCallback(
            activity, // LifecycleOwner
            activity.onBackPressedCallback
        )
        Timber.d("" + viewModel.storageDetailList.value!!.size)

        initView()
        bindViewModel()
    }

    fun initView() {
        initRecyclerView()

        binding.root.setOnClickListener(this)
        binding.constraintBack.setOnClickListener(this)
        binding.linearLayoutGoodInput.setOnClickListener(this)
        binding.linearLayoutGoodOutput.setOnClickListener(this)
    }


    private fun bindViewModel() {

        formRepository.storageGoods.observe(activity, Observer { newData ->
            Timber.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
            storageContentAdapter.submitList(
                viewModel.getStorageContent(
                    region,
                    map,
                    storageDetail.StorageNum
                )
            )
        })
    }


    private fun initRecyclerView() {
//        val mapList = ArrayList(resources.getStringArray(R.array.region_array).toList())
//        val mapDataList = mapList.mapIndexed { index, regionName ->
//            regionName
//        }
        storageContentAdapter = StorageContentAdapter(this)
        storageContentAdapter.submitList(
            viewModel.getStorageContent(
                region,
                map,
                storageDetail.StorageNum
            )
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

            R.id.linearLayoutGoodInput -> {
                listener.onGoodInputClick(map, region, storageDetail)
            }

            R.id.linearLayoutGoodOutput -> {
                listener.onGoodOutputClick(map, region, storageDetail)
            }
        }
    }

    interface Listener {
        fun onGoodInputClick(map: String, region: String, storageDetail: StorageDetail)
        fun onGoodOutputClick(map: String, region: String, storageDetail: StorageDetail)
    }


    /**
     * 點擊列顯示對應貨物資訊的Dialog
     * @param storageContentEntity 貨物資訊
     */
    override fun onItemClick(storageContentEntity: StorageContentEntity) {
        val goodFieldNameList = resources.getStringArray(R.array.storage_Good_field_name)
            .toList() as ArrayList<String>
        val goodFieldNameEngList =
            resources.getStringArray(R.array.storage_Good_field_name_eng)
                .toList() as ArrayList<String>
        val goodContent = ArrayList<String>()
        val goodJsonObject = jsonStringToJson(storageContentEntity.itemInformation)
        goodFieldNameEngList.forEach { key ->
            if (goodJsonObject.has(key)) {
                val value = goodJsonObject.getString(key)
                goodContent.add(value)
            } else {
                // Handle the case where the key is not present in the JSON object
                // You can choose to add a default value or take any other action
                goodContent.add("Key $key not found")
            }
        }
        val goodsDialog = GoodsDialog(
            isAdd = true,
            formItemFieldNameList = goodFieldNameList,
            formItemFieldNameEngList = goodFieldNameEngList,
            listener = this,
            formItemFieldContentList = goodContent
        )
        goodsDialog.show(activity.supportFragmentManager, "GoodsDialog")
    }

    override fun onGoodsDialogConfirm(formItemJson: JSONObject) {

    }

    override fun onChangeGoodsInfo(
        formItemJson: JSONObject,
        formGoodsDataWidget: FormGoodsDataWidget
    ) {
    }
}