package com.lhr.water.mapView.layer

import android.graphics.Canvas
import android.graphics.Matrix
import android.util.TypedValue
import android.view.MotionEvent
import com.lhr.water.mapView.MapView

/**
 * MapBaseLayer
 *
 * @author: onlylemi
 */
abstract class MapBaseLayer(protected var mapView: MapView?) {
    // layer show level
    var level = 0

    // layer is/not show
    var isVisible = true

    /**
     * touch event
     *
     * @param event
     */
    abstract fun onTouch(event: MotionEvent?, isAddPage: Boolean)

    /**
     * draw event
     *
     * @param canvas
     * @param currentMatrix
     * @param currentZoom
     * @param currentRotateDegrees
     */
    abstract fun draw(
        canvas: Canvas?, currentMatrix: Matrix?, currentZoom: Float,
        currentRotateDegrees: Float
    )

    protected fun setValue(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value, mapView!!.resources
                .displayMetrics
        )
    }

    companion object {
        // map layer level
        protected const val MAP_LEVEL = 0

        // location layer level
        protected const val LOCATION_LEVEL = Int.MAX_VALUE
    }
}