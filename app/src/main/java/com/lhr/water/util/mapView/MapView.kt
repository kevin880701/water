package com.lhr.water.mapView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Picture
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.lhr.water.mapView.layer.MapBaseLayer
import com.lhr.water.mapView.layer.MapLayer
import com.lhr.water.mapView.utils.MapMath.getDegreeBetweenTwoPoints
import com.lhr.water.mapView.utils.MapMath.getDistanceBetweenTwoPoints
import com.lhr.water.mapView.utils.MapMath.getMidPointBetweenTwoPoints
import com.lhr.water.mapView.utils.MapUtils.getPictureFromBitmap
import com.lhr.water.util.mapView.MapViewListener

/**
 * MapView
 *
 * @author: onlylemi
 */
class MapView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {
    private var mHolder: SurfaceHolder? = null
    private var mapViewListener: MapViewListener? = null

    /**
     * map is/not load finish
     *
     * @return
     */
    var isMapLoadFinish = false
        private set
    private var layers // all layers
            : MutableList<MapBaseLayer>? = null
    private var mapLayer: MapLayer? = null
    private var minZoom = 0.1f //圖片最多縮多小 (預設0.5f)
    private var maxZoom = 5.0f //圖片最多放多大
    private val startTouch = PointF()
    private val lastMove = PointF()
    private var mid = PointF()
    private val saveMatrix = Matrix()
    private val currentMatrix = Matrix()
    var currentZoom = 1.0f
    private var saveZoom = 0f
    private var currentRotateDegrees = 0.0f
    private var saveRotateDegrees = 0.0f
    private var currentTouchState = TOUCH_STATE_NO // default touch state
    private var oldDist = 0f
    private var oldDegree = 0f

    /**
     * setting scale&rotate is/not together on touch
     *
     * @param scaleAndRotateTogether
     */
    var isScaleAndRotateTogether = false

    init {
        initMapView()
    }

    /**
     * init mapview
     */
    private fun initMapView() {
        holder.addCallback(this)
        layers?.clear()
        layers = object : ArrayList<MapBaseLayer>() {
            override fun add(layer: MapBaseLayer): Boolean {
                if (layers!!.size != 0) {
                    if (layer.level >= this[size - 1].level) {
                        super.add(layer)
                    } else {
                        for (i in layers!!.indices) {
                            if (layer.level < this[i].level) {
                                super.add(i, layer)
                                break
                            }
                        }
                    }
                } else {
                    super.add(layer)
                }
                return true
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mHolder = holder
        refresh()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    /**
     * reload mapview
     */
    fun refresh() {
        if (mHolder != null) {
            val canvas = mHolder!!.lockCanvas()
            if (canvas != null) {
                canvas.drawColor(-1)
                if (isMapLoadFinish) {
                    for (layer in layers!!) {
                        if (layer.isVisible) {
                            layer.draw(canvas, currentMatrix, currentZoom, currentRotateDegrees)
                        }
                    }
                }
                mHolder!!.unlockCanvasAndPost(canvas)
            }
        }
    }

    fun loadMap(bitmap: Bitmap?) {
        loadMap(getPictureFromBitmap(bitmap!!))
    }

    /**
     * load map image
     *
     * @param picture
     */
    fun loadMap(picture: Picture?) {
        isMapLoadFinish = false
        Thread {
            if (picture != null) {
                if (mapLayer == null) {
                    mapLayer = MapLayer(this@MapView)
                    // add map image layer
                    layers!!.add(mapLayer!!)
                }
                mapLayer!!.setImage(picture)
                if (mapViewListener != null) {
                    // load map success, and callback
                    mapViewListener!!.onMapLoadSuccess()
                }
                isMapLoadFinish = true
                refresh()
            } else {
                if (mapViewListener != null) {
                    mapViewListener!!.onMapLoadFail()
                }
            }
        }.start()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isMapLoadFinish) {
            return false
        }
        val newDist: Float
        val newDegree: Float
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                for (layer in layers!!) {
                    layer.onTouch(event, true)
                }
                saveMatrix.set(currentMatrix)
                startTouch[event.x] = event.y
                lastMove[event.x] = event.y
                currentTouchState = TOUCH_STATE_SCROLL
            }
            MotionEvent.ACTION_POINTER_DOWN -> if (event.pointerCount == 2) {
                saveMatrix.set(currentMatrix)
                saveZoom = currentZoom
                saveRotateDegrees = currentRotateDegrees
                startTouch[event.getX(0)] = event.getY(0)
                currentTouchState = TOUCH_STATE_TWO_POINTED
                mid = midPoint(event)
                oldDist = distance(event, mid)
                oldDegree = rotation(event, mid)
            }
            MotionEvent.ACTION_UP -> {
                if (withFloorPlan(event.x, event.y)) {
//                    Log.i(TAG, event.getX() + " " + event.getY());
                    // layers on touch
                    for (layer in layers!!) {
                        layer.onTouch(event, false)
                    }
                }
                currentTouchState = TOUCH_STATE_NO
            }
            MotionEvent.ACTION_POINTER_UP -> currentTouchState = TOUCH_STATE_NO
            MotionEvent.ACTION_MOVE -> {
                for (layer in layers!!) {
                    layer.onTouch(event, true)
                }
                if (Math.abs(event.x - lastMove.x) > 0
                    && Math.abs(event.y - lastMove.y) > 0
                ) {
                    when (currentTouchState) {
                        TOUCH_STATE_SCROLL -> {
                            currentMatrix.set(saveMatrix)
                            currentMatrix.postTranslate(
                                event.x - startTouch.x, event.y -
                                        startTouch.y
                            )
                            refresh()
                        }

                        TOUCH_STATE_TWO_POINTED -> if (!isScaleAndRotateTogether) {
                            val x = oldDist
                            val y = getDistanceBetweenTwoPoints(
                                event.getX(0),
                                event.getY(0), startTouch.x, startTouch.y
                            )
                            val z = distance(event, mid)
                            val cos = (x * x + y * y - z * z) / (2 * x * y)
                            val degree = Math.toDegrees(Math.acos(cos.toDouble())).toFloat()
                            if (degree < 120 && degree > 45) {
                                oldDegree = rotation(event, mid)
                                currentTouchState = TOUCH_STATE_ROTATE
                            } else {
                                oldDist = distance(event, mid)
                                currentTouchState = TOUCH_STATE_SCALE
                            }
                        } else {
                            currentMatrix.set(saveMatrix)
                            newDist = distance(event, mid)
                            newDegree = rotation(event, mid)
                            val rotate = newDegree - oldDegree
                            var scale = newDist / oldDist
                            if (scale * saveZoom < minZoom) {
                                scale = minZoom / saveZoom
                            } else if (scale * saveZoom > maxZoom) {
                                scale = maxZoom / saveZoom
                            }
                            currentZoom = scale * saveZoom
                            currentRotateDegrees = ((newDegree - oldDegree + currentRotateDegrees)
                                    % 360)
                            currentMatrix.postScale(scale, scale, mid.x, mid.y)
                            currentMatrix.postRotate(rotate, mid.x, mid.y)
                            refresh()
                        }

                        TOUCH_STATE_SCALE -> {
                            currentMatrix.set(saveMatrix)
                            newDist = distance(event, mid)
                            //                            newDegree = rotation(event, mid);
//                            float rotate = newDegree - oldDegree;
                            var scale = newDist / oldDist
                            if (scale * saveZoom < minZoom) {
                                scale = minZoom / saveZoom
                            } else if (scale * saveZoom > maxZoom) {
                                scale = maxZoom / saveZoom
                            }
                            currentZoom = scale * saveZoom
                            currentMatrix.postScale(scale, scale, mid.x, mid.y)
                            refresh()
                        }

                        TOUCH_STATE_ROTATE -> {
                            currentMatrix.set(saveMatrix)
                            newDegree = rotation(event, mid)
                            val rotate = newDegree - oldDegree
                            currentRotateDegrees = (rotate + saveRotateDegrees) % 360
                            currentRotateDegrees =
                                if (currentRotateDegrees > 0) currentRotateDegrees else currentRotateDegrees + 360
                            currentMatrix.postRotate(rotate, mid.x, mid.y)
                            refresh()
                        }

                        else -> {}
                    }
                    lastMove[event.x] = event.y
                }
            }
            else -> {}
        }
        return true
    }

    /**
     * set mapview listener
     *
     * @param mapViewListener
     */
    fun setMapViewListener(mapViewListener: MapViewListener?) {
        this.mapViewListener = mapViewListener
    }

    /**
     * convert coordinate of map to coordinate of screen
     *
     * @param x
     * @param y
     * @return
     */
    fun convertMapXYToScreenXY(x: Float, y: Float): FloatArray {
        val invertMatrix = Matrix()
        val value = floatArrayOf(x, y)
        currentMatrix.invert(invertMatrix)
        invertMatrix.mapPoints(value)
        return value
    }

    /**
     * add layer
     *
     * @param layer
     */
    fun addLayer(layer: MapBaseLayer?) {
        if (layer != null) {
            layers!!.add(layer)
        }
    }

    /**
     * get all layers
     *
     * @return
     */
    fun getLayers(): List<MapBaseLayer>? {
        return layers
    }

    fun translate(x: Float, y: Float) {
        currentMatrix.postTranslate(x, y)
    }

    /**
     * set point to map center
     *
     * @param x
     * @param y
     */
    fun mapCenterWithPoint(x: Float, y: Float) {
        val goal = floatArrayOf(x, y)
        currentMatrix.mapPoints(goal)
        val deltaX = width / 2 - goal[0]
        val deltaY = height / 2 - goal[1]
        currentMatrix.postTranslate(deltaX, deltaY)
    }

    fun getCurrentRotateDegrees(): Float {
        return currentRotateDegrees
    }

    /**
     * set rotate degrees
     *
     * @param degrees
     */
    fun setCurrentRotateDegrees(degrees: Float) {
        mapCenterWithPoint(mapWidth / 2, mapHeight / 2)
        setCurrentRotateDegrees(degrees, (width / 2).toFloat(), (height / 2).toFloat())
    }

    /**
     * set rotate degrees
     *
     * @param degrees
     * @param x
     * @param y
     */
    fun setCurrentRotateDegrees(degrees: Float, x: Float, y: Float) {
        currentMatrix.postRotate(degrees - currentRotateDegrees, x, y)
        currentRotateDegrees = degrees % 360
        currentRotateDegrees = if (currentRotateDegrees > 0) currentRotateDegrees else currentRotateDegrees + 360
    }


    fun setMaxZoom(maxZoom: Float) {
        this.maxZoom = maxZoom
    }

    fun setMinZoom(minZoom: Float) {
        this.minZoom = minZoom
    }

    fun setCurrentZoom(zoom: Float, x: Float, y: Float) {
        currentMatrix.postScale(zoom / currentZoom, zoom / currentZoom, x, y)
        currentZoom = zoom
    }

    private fun midPoint(event: MotionEvent): PointF {
        return getMidPointBetweenTwoPoints(
            event.getX(0), event.getY(0), event.getX(1), event.getY(1)
        )
    }

    private fun distance(event: MotionEvent, mid: PointF): Float {
        return getDistanceBetweenTwoPoints(
            event.getX(0), event.getY(0), mid.x, mid.y
        )
    }

    private fun rotation(event: MotionEvent, mid: PointF): Float {
        return getDegreeBetweenTwoPoints(
            event.getX(0), event.getY(0), mid.x, mid.y
        )
    }

    /**
     * point is/not in floor plan
     *
     * @param x
     * @param y
     * @return
     */
    fun withFloorPlan(x: Float, y: Float): Boolean {
        val goal = convertMapXYToScreenXY(x, y)
        return goal[0] > 0 && goal[0] < mapLayer!!.getImage()!!.width && goal[1] > 0 && goal[1] < mapLayer!!.getImage()!!.height
    }

    val mapWidth: Float
        get() = mapLayer!!.getImage()!!.width.toFloat()
    val mapHeight: Float
        get() = mapLayer!!.getImage()!!.height.toFloat()

    companion object {
        private const val TAG = "MapView"
        private const val TOUCH_STATE_NO = 0 // no touch
        private const val TOUCH_STATE_SCROLL = 1 // scroll(one point)
        private const val TOUCH_STATE_SCALE = 2 // scale(two points)
        private const val TOUCH_STATE_ROTATE = 3 // rotate(two points)
        private const val TOUCH_STATE_TWO_POINTED = 4 // two points touch
    }
}