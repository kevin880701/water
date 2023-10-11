package com.lhr.water.ui.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lhr.water.mapView.layer.MarkLayer
import com.lhr.water.model.Model.Companion.allTargetDataArrayList
import com.lhr.water.R
import com.lhr.water.databinding.ActivityMapBinding
import com.lhr.water.model.TargetData
import com.lhr.water.util.mapView.MapViewListener
import com.lhr.water.util.popupWindow.MarkInformationPopupWindow
import java.io.IOException
import java.lang.reflect.Field


class MapActivity(): AppCompatActivity() {

    lateinit var viewModel: MapViewModel
    lateinit var binding: ActivityMapBinding

    private var markLayer: MarkLayer? = null
    lateinit var targetDataArrayList: ArrayList<TargetData>
    lateinit var region: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        viewModel = ViewModelProvider(
            this,
            MapViewModelFactory(this.application)
        )[MapViewModel::class.java]
        binding.viewModel = viewModel
        region = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("region", String::class.java) as String
        } else {
            intent.getSerializableExtra("region") as String
        }

        targetDataArrayList = allTargetDataArrayList.filter { it.targetRegion == region } as ArrayList<TargetData>
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(this.assets.open(region + ".png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        binding.mapView.loadMap(bitmap)
        binding.mapView.setMapViewListener(object : MapViewListener {
            override fun onMapLoadSuccess() {
                markLayer = MarkLayer(binding.mapView, targetDataArrayList)
                markLayer!!.setMarkIsClickListener(object : MarkLayer.MarkIsClickListener {
                    override fun markIsClick(num: Int) {
                        if(markLayer!!.MARK_ALLOW_CLICK){
                            val choose = MarkInformationPopupWindow(this@MapActivity, targetDataArrayList[num])
                            val mLayoutInScreen: Field =
                                PopupWindow::class.java.getDeclaredField("mLayoutInScreen")
                            mLayoutInScreen.isAccessible = true
                            mLayoutInScreen.set(choose, true)
                            choose.isClippingEnabled = false
                            val view: View = LayoutInflater.from(this@MapActivity).inflate(
                                R.layout.popup_mark_name,
                                null
                            )
                            choose.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0)
                        }
                    }})
                binding.mapView.addLayer(markLayer)
                binding.mapView.refresh()
            }

            override fun onMapLoadFail() {}
        })
    }


}