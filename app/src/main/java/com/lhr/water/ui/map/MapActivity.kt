package com.lhr.water.ui.map

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
import com.lhr.water.databinding.ActivityMapBinding
import com.lhr.water.mapView.layer.MarkLayer
import com.lhr.water.room.RegionEntity
import com.lhr.water.room.StorageEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.mapView.MapViewListener
import com.lhr.water.util.widget.QuickSearchBottom
import com.lhr.water.util.widget.StorageContentBottom
import com.lhr.water.util.widget.StorageInfoBottom
import java.io.IOException


class MapActivity(): BaseActivity(), View.OnClickListener, StorageInfoBottom.Listener{
    val viewModel: MapViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}
    private var _binding: ActivityMapBinding? = null
    private val binding get() = _binding!!

    var backView: RelativeLayout? = null
    private var markLayer: MarkLayer? = null
    lateinit var regionEntity: RegionEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryBlue, null)

        // 檢查版本判斷接收資料方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            regionEntity = intent.getSerializableExtra("regionEntity") as RegionEntity
        } else {
            regionEntity = intent.getSerializableExtra("regionEntity") as RegionEntity
        }

        viewModel.setStorageEntityList(regionEntity)

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
        bindViewModel()
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = "${regionEntity.deptName}-${regionEntity.mapSeq}"
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        backView = binding.relativeLayoutBackView
        setupBackButton(binding.widgetTitleBar.imageBack)
        initMapView()
        binding.constraintSearch.setOnClickListener(this)
    }

    private fun bindViewModel() {
    }

    private fun initMapView() {
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(this.assets.open( "map/${regionEntity.regionName},${regionEntity.deptName},${regionEntity.deptNumber}-${regionEntity.mapSeq}.jpg"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        binding.mapView.loadMap(bitmap)
        binding.mapView.setMapViewListener(object : MapViewListener {
            override fun onMapLoadSuccess() {
                markLayer = MarkLayer(binding.mapView, viewModel)
                markLayer!!.setMarkIsClickListener(object : MarkLayer.MarkIsClickListener {
                    override fun markIsClick(num: Int) {
                        if(markLayer!!.MARK_ALLOW_CLICK){
                            showStorageInfo(viewModel.storageEntityList.value!![num])
                        }
                    }})
                binding.mapView.addLayer(markLayer)
                binding.mapView.refresh()
            }

            override fun onMapLoadFail() {}
        })
    }

    fun showStorageInfo(storageEntity: StorageEntity) {
        val storageInfoBottom = StorageInfoBottom(this, this, storageEntity)
        showBottomSheet(storageInfoBottom)
    }
    fun showBottomSheet(view: View?) {
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
            R.id.constraintSearch -> {
                val quickSearchBottom = QuickSearchBottom(this, regionEntity)
                showBottomSheet(quickSearchBottom)
            }
        }
    }

    override fun onStorageContentClick(storageEntity: StorageEntity) {
        val storageContentBottom = StorageContentBottom(this, storageEntity)
        showBottomSheet(storageContentBottom)
    }
}