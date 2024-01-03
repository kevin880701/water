package com.lhr.water.mapView.layer

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Picture
import android.util.Log
import android.view.MotionEvent
import com.lhr.water.mapView.MapView

/**
 * MapLayer
 *
 * @author: onlylemi
 */
class MapLayer(mapView: MapView?) : MapBaseLayer(mapView) {
    private var image: Picture? = null
    private var hasMeasured = false


    fun setImage(image: Picture?) {
        this.image = image
        if (mapView!!.width == 0) {
            val vto = mapView!!.viewTreeObserver
            vto.addOnPreDrawListener {
                if (!hasMeasured) {
                    initMapLayer()
                    hasMeasured = true
                }
                true
            }
        } else {
            initMapLayer()
        }
    }

    /**
     * init map image layer
     */
    private fun initMapLayer() {
        val zoom = getInitZoom(
            mapView!!.width.toFloat(), mapView!!.height.toFloat(), image!!.width.toFloat(), image!!
                .getHeight().toFloat()
        )
        Log.i(TAG, java.lang.Float.toString(zoom))
        mapView!!.setCurrentZoom(zoom, 0f, 0f)
        val width = mapView!!.width - zoom * image!!.width
        val height = mapView!!.height - zoom * image!!.height
        mapView!!.translate(width / 2, height / 2)
    }

    /**
     * calculate init zoom
     *
     * @param viewWidth
     * @param viewHeight
     * @param imageWidth
     * @param imageHeight
     * @return
     */
    private fun getInitZoom(
        viewWidth: Float, viewHeight: Float, imageWidth: Float,
        imageHeight: Float
    ): Float {
        val widthRatio = viewWidth / imageWidth
        val heightRatio = viewHeight / imageHeight
        Log.i(TAG, "widthRatio:$widthRatio")
        Log.i(TAG, "widthRatio:$heightRatio")
        if (widthRatio * imageHeight <= viewHeight) {
            return widthRatio
        } else if (heightRatio * imageWidth <= viewWidth) {
            return heightRatio
        }
        return 0F
    }

    override fun onTouch(event: MotionEvent?, isAddPage: Boolean) {}

    override fun draw(canvas: Canvas?, currentMatrix: Matrix?, currentZoom: Float, currentRotateDegrees: Float) {
        canvas!!.save()
        canvas!!.setMatrix(currentMatrix)
        if (image != null) {
            canvas!!.drawPicture(image!!)
        }
        canvas!!.restore()
    }

    fun getImage(): Picture? {
        return image
    }

    companion object {
        private const val TAG = "MapLayer"
    }
}