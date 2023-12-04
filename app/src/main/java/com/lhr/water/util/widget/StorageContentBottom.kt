package com.lhr.water.util.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.data.StorageDetail
import com.lhr.water.databinding.WidgetBottomStorageContentBinding
import com.lhr.water.ui.map.MapActivity
import timber.log.Timber

class StorageContentBottom : RelativeLayout, View.OnClickListener {
    private var binding: WidgetBottomStorageContentBinding
    private val listener: Listener
    private val activity: MapActivity
    private val storageDetail: StorageDetail
    private val map: String
    private val region: String
    constructor(
        listener: Listener,
        activity: MapActivity,
        storageDetail: StorageDetail,
        map: String,
        region: String
    ) : super(activity) {
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

        initView()
    }

    fun initView(){
        binding.root.setOnClickListener(this)
        binding.constraintBack.setOnClickListener(this)
        binding.linearLayoutGoodInput.setOnClickListener(this)
        binding.linearLayoutGoodOutput.setOnClickListener(this)
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

    interface Listener{
        fun onGoodInputClick(map: String, region: String, storageDetail: StorageDetail)
        fun onGoodOutputClick(map: String, region: String, storageDetail: StorageDetail)
    }
}