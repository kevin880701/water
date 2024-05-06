package com.lhr.water.util.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetBottomStorageInfoBinding
import com.lhr.water.repository.RegionRepository
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.util.MapDataList

class StorageInfoBottom : RelativeLayout, View.OnClickListener {
    private var binding: WidgetBottomStorageInfoBinding
    private val listener: Listener
    private val activity: MapActivity
    private val storageEntity: StorageEntity
    private val regionRepository: RegionRepository
    constructor(
        listener: Listener,
        activity: MapActivity,
        storageEntity: StorageEntity,
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_bottom_storage_info, this, true
        )
        this@StorageInfoBottom.listener = listener
        this@StorageInfoBottom.activity = activity
        this@StorageInfoBottom.storageEntity = storageEntity
        this@StorageInfoBottom.regionRepository = RegionRepository.getInstance(activity)
        activity.onBackPressedDispatcher.addCallback(
            activity, // LifecycleOwner
            activity.onBackPressedCallback
        )

        initView()
    }

    fun initView(){
        val firstMatchingRegion = MapDataList.find { it.deptNumber == storageEntity.deptNumber }
        val firstMatchingStorageRecord = regionRepository.storageEntities.find { it.storageId == storageEntity.storageId }
        binding.textRegionName.text = firstMatchingRegion?.regionName
        binding.textDeptName.text = firstMatchingRegion?.deptName
        binding.textStorageName.text = firstMatchingStorageRecord?.storageName

        binding.root.setOnClickListener(this)
        binding.constraintBack.setOnClickListener(this)
        binding.linearLayoutStorageContent.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.constraintBack -> {
                activity.onBackPressedCallback.handleOnBackPressed()
            }
            R.id.imageBack -> {
                activity.onBackPressedCallback.handleOnBackPressed()
            }
            R.id.linearLayoutStorageContent -> {
                listener.onStorageContentClick(storageEntity)
            }
        }
    }

    interface Listener{
        fun onStorageContentClick(storageEntity: StorageEntity)
    }
}