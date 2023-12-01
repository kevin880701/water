package com.lhr.water.ui.map.InfoDetailBottom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.lhr.water.R
import com.lhr.water.data.StorageDetail
import com.lhr.water.databinding.WidgetBottomInfoDetailBinding
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.ui.storageContent.StorageContentActivity
import timber.log.Timber

class InfoDetailBottom : RelativeLayout, View.OnClickListener {
    private var binding: WidgetBottomInfoDetailBinding
    private val activity: MapActivity
    private val storageDetail: StorageDetail
    private val map: String
    private val region: String
    constructor(
        activity: MapActivity,
        storageDetail: StorageDetail,
        map: String,
        region: String
    ) : super(activity) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.widget_bottom_info_detail, this, true
        )
        this@InfoDetailBottom.activity = activity
        this@InfoDetailBottom.storageDetail = storageDetail
        this@InfoDetailBottom.map = map
        this@InfoDetailBottom.region = region

        activity.onBackPressedDispatcher.addCallback(
            activity, // LifecycleOwner
            activity.onBackPressedCallback
        )

        initView()
    }

    fun initView(){
        binding.textMapName.text = map
        binding.textRegionName.text = region
        binding.textStorageName.text = storageDetail.StorageName
        binding.textStorageNum.text = storageDetail.StorageNum

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
                val intent = Intent(activity, StorageContentActivity::class.java)
                activity.startActivity(intent)
            }
            R.id.linearLayoutGoodInput -> {
                Timber.d("linearLayoutStorageContent 點擊!")
            }
            R.id.linearLayoutGoodOutput -> {
                Timber.d("linearLayoutStorageContent 點擊!")
            }
        }
    }
}