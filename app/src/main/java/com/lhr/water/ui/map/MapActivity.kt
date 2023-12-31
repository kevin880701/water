package com.lhr.water.ui.map

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.R
import com.lhr.water.data.StorageDetail
import com.lhr.water.databinding.ActivityMapBinding
import com.lhr.water.mapView.layer.MarkLayer
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.storageGoodInput.StorageGoodInputActivity
import com.lhr.water.util.mapView.MapViewListener
import com.lhr.water.util.widget.StorageInfoBottom
import timber.log.Timber
import java.io.IOException


class MapActivity(): BaseActivity(), View.OnClickListener, StorageInfoBottom.Listener, StorageContentBottom.Listener{
    private val viewModel: MapViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}
    private var _binding: ActivityMapBinding? = null
    private val binding get() = _binding!!

    var backView: RelativeLayout? = null
    private var markLayer: MarkLayer? = null
    lateinit var region: String
    lateinit var map: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)

        // 檢查版本判斷接收資料方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            region = intent.getParcelableExtra("region", String::class.java) as String
            map = intent.getParcelableExtra("map", String::class.java) as String
        } else {
            region = intent.getSerializableExtra("region") as String
            map = intent.getSerializableExtra("map") as String
        }

        viewModel.setStorageDetailList(region, map)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backView!!.childCount > 0) {
                    cancelBottomSheet()
                }else{
                    finish()
                }
            }
        }

        initView()
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = map
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        backView = binding.relativeLayoutBackView
        setupBackButton(binding.widgetTitleBar.imageBack)
        initMapView()
    }

    private fun bindViewModel() {

    }
    private fun initMapView() {
//        viewModel.setTargetDataArrayList(region, map)
        Timber.d(map)
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(this.assets.open( "map/$region/$map.jpg"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        binding.mapView.loadMap(bitmap)
        binding.mapView.setMapViewListener(object : MapViewListener {
            override fun onMapLoadSuccess() {
                markLayer = MarkLayer(binding.mapView, viewModel.storageDetailList.value)
                markLayer!!.setMarkIsClickListener(object : MarkLayer.MarkIsClickListener {
                    override fun markIsClick(num: Int) {
                        if(markLayer!!.MARK_ALLOW_CLICK){
                            showStorageInfo(viewModel.storageDetailList.value!![num])
                        }
                    }})
                binding.mapView.addLayer(markLayer)
                binding.mapView.refresh()
            }

            override fun onMapLoadFail() {}
        })
    }

    fun showStorageInfo(storageDetail: StorageDetail) {
        val storageInfoBottom = StorageInfoBottom(this, this, storageDetail, map, region)
        showBottomSheet(storageInfoBottom)
    }
    private fun showBottomSheet(view: View?) {
        if (backView == null) {
            return
        }
        backView!!.setBackgroundColor(Color.parseColor("#9e000000"))
        val t: Transition = Slide(Gravity.BOTTOM)
        TransitionManager.beginDelayedTransition(backView, t)
        backView!!.addView(view)
    }

    fun cancelBottomSheet() {
        if (backView == null) {
            return
        }

        val t: Transition = Slide(Gravity.BOTTOM)
        t.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {}
            override fun onTransitionEnd(transition: Transition) {
                TransitionManager.endTransitions(backView)
                if (backView!!.childCount <= 0) {
                    backView!!.setBackgroundColor(Color.parseColor("#00000000"))
                }
            }

            override fun onTransitionCancel(transition: Transition) {}
            override fun onTransitionPause(transition: Transition) {}
            override fun onTransitionResume(transition: Transition) {}
        })
        TransitionManager.beginDelayedTransition(backView, t)
        if (backView!!.childCount > 0) {
            backView!!.removeViewAt(backView!!.childCount - 1)
        }
        onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            onBackPressedCallback
        )
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    override fun onStorageContentClick(map: String, region: String, storageDetail: StorageDetail) {
        val storageContentBottom = StorageContentBottom(this, this, storageDetail, map, region)
        showBottomSheet(storageContentBottom)
    }

    override fun onGoodInputClick(map: String, region: String, storageDetail: StorageDetail) {
        val intent = Intent(this, StorageGoodInputActivity::class.java)
        intent.putExtra("region", region)
        intent.putExtra("map", map)
        intent.putExtra("storageNum", storageDetail.StorageNum)
        startActivity(intent)
    }

    override fun onGoodOutputClick(map: String, region: String, storageDetail: StorageDetail) {

    }
}