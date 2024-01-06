package com.lhr.water.ui.map

import android.app.Activity
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
import com.lhr.water.databinding.ActivityMapBinding
import com.lhr.water.mapView.layer.AddMarkLayer
import com.lhr.water.mapView.layer.MarkLayer
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.mapView.MapViewListener
import com.lhr.water.util.showToast
import timber.log.Timber
import java.io.IOException


class AddPointActivity(): BaseActivity(), View.OnClickListener{
    private val viewModel: MapViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}
    private var _binding: ActivityMapBinding? = null
    private val binding get() = _binding!!

    var backView: RelativeLayout? = null
    private var addMarkLayer: AddMarkLayer? = null
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
        bindViewModel()
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = getString(R.string.choose_storage_point)
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        binding.buttonConfirm.visibility = View.VISIBLE
        setupBackButton(binding.widgetTitleBar.imageBack)
        binding.buttonConfirm.setOnClickListener(this)
        initMapView()
    }

    private fun bindViewModel() {
        viewModel.regionRepository.storageEntities.observe(this) {
            viewModel.setStorageDetailList(region, map)
            binding.mapView.refresh()
        }

    }

    private fun initMapView() {
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(this.assets.open( "map/$region/$map.jpg"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        binding.mapView.loadMap(bitmap)
        binding.mapView.setMapViewListener(object : MapViewListener {
            override fun onMapLoadSuccess() {
                addMarkLayer = AddMarkLayer(binding.mapView)
                addMarkLayer!!.setMarkIsClickListener(object : AddMarkLayer.MarkIsClickListener {
                    override fun markIsClick(num: Int) {
                    }})
                binding.mapView.addLayer(addMarkLayer)
                binding.mapView.refresh()
            }

            override fun onMapLoadFail() {}
        })
        binding.widgetTitleBar.imageAdd.setOnClickListener(this)
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
            R.id.buttonConfirm -> {
                if(addMarkLayer?.clickX == 0f || addMarkLayer?.clickY == 0f){
                    showToast(this, "未選擇位置")
                }else{
                    val resultIntent = Intent()
                    addMarkLayer?.let { resultIntent.putExtra("pointX", it.clickX) }
                    addMarkLayer?.let { resultIntent.putExtra("pointY", it.clickY) }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }
}