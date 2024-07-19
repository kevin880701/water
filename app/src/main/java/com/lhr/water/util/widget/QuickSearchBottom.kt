package com.lhr.water.util.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.WidgetQuickSearchBinding
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.RegionEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.ui.map.MapViewModel
import com.lhr.water.util.adapter.StorageContentAdapter

class QuickSearchBottom(
    var activity: MapActivity,
    var regionEntity: RegionEntity
) : RelativeLayout(activity), View.OnClickListener {

    val viewModel: MapViewModel by activity.viewModels {
        (activity.applicationContext as APP).appContainer.viewModelFactory
    }
    private var binding: WidgetQuickSearchBinding
    private lateinit var storageContentAdapter: StorageContentAdapter
    val formRepository: FormRepository by lazy { FormRepository.getInstance(activity) }

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_quick_search, this, true
        )

        activity.onBackPressedDispatcher.addCallback(
            activity, // LifecycleOwner
            activity.onBackPressedCallback
        )

        initView()
    }

    fun initView() {

        binding.root.setOnClickListener(this)
        binding.constraintBack.setOnClickListener(this)
        binding.buttonConfirm.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonConfirm -> {
                val result = activity.viewModel.regionRepository.storageEntities.find {
                    it.deptNumber == regionEntity.deptNumber && it.mapSeq == regionEntity.mapSeq && it.storageName == binding.editStorageName.text.toString()
                }

                if (result == null) {
                    Toast.makeText(context, activity.getString(R.string.storage_name_wrong), Toast.LENGTH_SHORT).show()
                }else{
                    activity.onBackPressedCallback.handleOnBackPressed()
                    val storageContentBottom = StorageContentBottom(activity, result)
                    activity.showBottomSheet(storageContentBottom)
                }
            }

            R.id.imageBack -> {
                activity.onBackPressedCallback.handleOnBackPressed()
            }
        }
    }

}