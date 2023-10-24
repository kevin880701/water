package com.lhr.water.util.widget

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.databinding.BottomInfoDetailBinding
import com.lhr.water.model.TargetData
import com.lhr.water.ui.map.MapActivity
import timber.log.Timber

class InfoDetailBottom : RelativeLayout, View.OnClickListener {
    private var binding: BottomInfoDetailBinding
    private val activity: MapActivity
    private val targetData: TargetData
    constructor(
        activity: MapActivity,
        targetData: TargetData
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.bottom_info_detail, this, true
        )
        this@InfoDetailBottom.activity = activity
        this@InfoDetailBottom.targetData = targetData

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
        binding.textCurrentMapName.text = targetData.targetRegion
        binding.textCurrentRegionName.text = targetData.targetMap
        binding.textCurrentStorageName.text = targetData.targetName
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