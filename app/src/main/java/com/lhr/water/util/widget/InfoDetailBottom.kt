package com.lhr.water.util.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.data.StorageDetail
import com.lhr.water.databinding.WidgetBottomInfoDetailBinding
import com.lhr.water.ui.map.MapActivity
import timber.log.Timber

class InfoDetailBottom : RelativeLayout, View.OnClickListener {
    private var binding: WidgetBottomInfoDetailBinding
    private val activity: MapActivity
    private val storageDetail: StorageDetail
    constructor(
        activity: MapActivity,
        storageDetail: StorageDetail
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_bottom_info_detail, this, true
        )
        this@InfoDetailBottom.activity = activity
        this@InfoDetailBottom.storageDetail = storageDetail

        activity.onBackPressedDispatcher.addCallback(
            activity, // LifecycleOwner
            activity.onBackPressedCallback
        )

        initView()
        binding.root.setOnClickListener(this)
        binding.constraintBack.setOnClickListener(this)
        binding.linearLayoutStorageContent.setOnClickListener(this)
    }

    fun initView(){
        binding.textCurrentMapName.text = storageDetail.StorageName
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
                Timber.d("linearLayoutStorageContent 點擊!")
            }
        }
    }
}