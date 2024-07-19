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
import com.lhr.water.util.dialog.MaterialDialog
import org.json.JSONObject

class StorageContentBottom(
    var activity: MapActivity,
    var storageEntity: StorageEntity,
) : RelativeLayout(activity), View.OnClickListener, StorageContentAdapter.Listener, MaterialDialog.Listener {

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
    override fun onMaterialClick(storageRecordEntity: StorageRecordEntity) {
        val materialDialog = MaterialDialog(
            storageRecordEntity = storageRecordEntity,
            listener = this,
        )
        materialDialog.show(activity.supportFragmentManager, "MaterialDialog")
    }

    override fun onGoodsDialogConfirm(formItemJson: JSONObject) {

    }
}