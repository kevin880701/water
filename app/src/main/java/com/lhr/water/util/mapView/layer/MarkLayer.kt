package com.lhr.water.mapView.layer

import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.lhr.water.mapView.MapView
import com.lhr.water.mapView.utils.MapMath.getDistanceBetweenTwoPoints
import com.lhr.water.R
import com.lhr.water.data.StorageDetail
import com.lhr.water.model.Model.Companion.markDrawableIdMap

/**
 * MarkLayer
 *
 * @author: onlylemi
 */
class MarkLayer(
    mapView: MapView?,
    private var targetDataArrayList: ArrayList<StorageDetail>? = null
) : MapBaseLayer(mapView) {
    private var listener: MarkIsClickListener? = null
    private var bmpMarkTouch: Bitmap? = null
    private var radiusMark = 0f
    var markBitmapMap = HashMap<Int,Bitmap>()
    var isClickMark = false
    var MARK_ALLOW_CLICK = false

        private set
    var num = -1
    private var paint: Paint? = null

    init {
        initLayer()
    }

    private fun initLayer() {
        radiusMark = setValue(10f)
        // 使用png檔直接使用下面這行
//        bmpMark = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.test_icon);
        // 將xml(vector)轉成Bitmap使用
        createMarkDrawable()

        // 使用png檔直接使用下面這行
//        bmpMarkTouch = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.mark_touch);
        // 將xml(vector)轉成Bitmap使用
        val markTouchDrawable = mapView!!.resources.getDrawable(R.drawable.mark_touch, mapView!!.context.theme)
        markTouchDrawable.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(-0xe7e7e8, BlendModeCompat.SRC_ATOP)
        bmpMarkTouch = Bitmap.createBitmap(
            markTouchDrawable.intrinsicWidth,
            markTouchDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val markTouchCanvas = Canvas(bmpMarkTouch!!)
        markTouchDrawable.setBounds(0, 0, markTouchCanvas.width, markTouchCanvas.height)
        markTouchDrawable.draw(markTouchCanvas)
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onTouch(event: MotionEvent?) {
        if (targetDataArrayList != null) {
            if (!targetDataArrayList!!.isEmpty()) {
                val goal = mapView!!.convertMapXYToScreenXY(event!!.x, event!!.y)
                Log.v("PPP", "" + event!!.x + ":" + event!!.y)
                //點擊出現偏差 所以減50
                goal[0] = goal[0] - 45
                goal[1] = goal[1] - 50
                Log.v("LLL", "" + goal[0] + ":" + goal[1])
                for (i in targetDataArrayList!!.indices) {
                    if (getDistanceBetweenTwoPoints(
                            goal[0], goal[1],
                            targetDataArrayList!![i].StorageX.toFloat() - (markBitmapMap[0]!!.width / 2), targetDataArrayList!![i].StorageY.toFloat() - markBitmapMap[0]!!.getHeight() / 2
                        ) <= 50
                    ) {
                        num = i
                        isClickMark = true
                        break
                    }
                    if (i == targetDataArrayList!!.size - 1) {
                        isClickMark = false
                    }
                }
            }
            if (listener != null && isClickMark) {
                listener!!.markIsClick(num)
                mapView!!.refresh()
            }
        }
    }

    override fun draw(canvas: Canvas?, currentMatrix: Matrix?, currentZoom: Float, currentRotateDegrees: Float) {
        if (isVisible && targetDataArrayList != null) {
            canvas!!.save()
            if (!targetDataArrayList!!.isEmpty()) {
                for (i in targetDataArrayList!!.indices) {
//                    val mark = marks!![i]
                    val goal = floatArrayOf(targetDataArrayList!![i].StorageX.toFloat(), targetDataArrayList!![i].StorageY.toFloat())
                    currentMatrix!!.mapPoints(goal)
                    paint!!.color = Color.BLACK
                    paint!!.textSize = radiusMark

                    //mark name 當地圖放大到一定程度才顯示mark
                    // 原本mapView!!.currentZoom > 0.5，但因為希望不放大也能直接按所以設0.0
                    if (mapView!!.currentZoom > 0.0 && targetDataArrayList!![i] != null && targetDataArrayList!!.size == targetDataArrayList!!.size) {
                        // 隨著地圖放大縮小改變圖標透明度
                        MARK_ALLOW_CLICK = true
//                        if(mapView!!.currentZoom<1.2) {
//                            paint!!.alpha = (250 * (1 - (1.2 - mapView!!.currentZoom) / 0.7)).toInt()
//                        }
//                        canvas.drawText(
//                            targetDataArrayList!![i].targetName, goal[0] - radiusMark, goal[1] -
//                                    radiusMark / 2 + 75, paint!!
//                        )
                        //mark ico
                        canvas.drawBitmap(
                            markBitmapMap[0]!!, goal[0] - markBitmapMap[0]!!.width / 2,
                            goal[1] - markBitmapMap[0]!!.height / 2, paint
                        )
                        if (i == num && isClickMark) {
                            canvas.drawBitmap(
                                bmpMarkTouch!!, goal[0] - bmpMarkTouch!!.width / 2,
                                goal[1] - bmpMarkTouch!!.height, paint
                            )
                        }
                    }else{
                        MARK_ALLOW_CLICK = false
                    }
                }
            }
            canvas.restore()
        }
    }

    private fun createMarkDrawable() {
        markDrawableIdMap.forEach { (key, drawableId) ->
            val markDrawable = mapView!!.resources.getDrawable(drawableId, mapView!!.context.theme)
            markDrawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(ContextCompat.getColor(mapView!!.context, R.color.md_theme_light_primary), BlendModeCompat.SRC_ATOP)
            var bmpMark = Bitmap.createBitmap(markDrawable.intrinsicWidth, markDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val markCanvas = Canvas(bmpMark!!)
            markDrawable.copyBounds()
            markDrawable.setBounds(0, 0, markCanvas.width, markCanvas.height)
            markDrawable.draw(markCanvas)
            markBitmapMap.set(key,bmpMark!!)
        }
    }

    fun setMarkIsClickListener(listener: MarkIsClickListener?) {
        this.listener = listener
    }

    interface MarkIsClickListener {
        fun markIsClick(num: Int)
    }
}