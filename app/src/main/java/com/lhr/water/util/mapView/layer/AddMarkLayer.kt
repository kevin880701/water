package com.lhr.water.mapView.layer

import android.graphics.*
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.lhr.water.mapView.MapView
import com.lhr.water.R
import com.lhr.water.model.Model.Companion.markDrawableIdMap
import java.lang.Math.abs

/**
 * MarkLayer
 *
 * @author: onlylemi
 */
class AddMarkLayer(
    mapView: MapView?
) : MapBaseLayer(mapView) {
    private var listener: MarkIsClickListener? = null
    private var bmpMarkTouch: Bitmap? = null
    private var radiusMark = 0f
    private var markBitmapMap = HashMap<Int, Bitmap>()
    private var num = -1
    private var paint: Paint? = null
    var clickX = 0f
    var clickY = 0f
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private val dragThreshold = 5f // 移動距離閥值
    private var hasDragged = false // 是否已經觸發了拖動

    init {
        initLayer()
    }

    private fun initLayer() {
        radiusMark = setValue(10f)
        createMarkDrawable()
        // 將xml(vector)轉成Bitmap使用
        val markTouchDrawable =
            mapView!!.resources.getDrawable(R.drawable.mark_touch, mapView!!.context.theme)
        markTouchDrawable.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                -0xe7e7e8,
                BlendModeCompat.SRC_ATOP
            )
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

    override fun onTouch(event: MotionEvent?, isAddPage: Boolean) {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // 用戶按下時的操作（點擊）
                    lastX = event.x
                    lastY = event.y
                    hasDragged = false // 重置標志
                }

                MotionEvent.ACTION_MOVE -> {
                    // 用戶移動時的操作（拖動）
                    val deltaX = event.x - lastX
                    val deltaY = event.y - lastY

                    // 判斷是否已經觸發拖動
                    if (!hasDragged && (abs(deltaX) > dragThreshold || abs(deltaY) > dragThreshold)) {
                        hasDragged = true
                        // 在這裡處理拖動開始時的操作
                    }
                    // 更新 lastX 和 lastY，以便下一次計算偏移量
                    lastX = event.x
                    lastY = event.y
                }

                MotionEvent.ACTION_UP -> {
                    if (!hasDragged) {
                        // 未達到閾值，可以視為點擊操作
                        val goal = mapView!!.convertMapXYToScreenXY(event!!.x, event!!.y)
                        //點擊出現偏差 所以減50
                        goal[0] = goal[0] - 45
                        goal[1] = goal[1] - 50
                        clickX = goal[0]
                        clickY = goal[1]
                        if (listener != null) {
                            listener!!.markIsClick(num)
                            mapView!!.refresh()
                        }
                    } else {
                    }
                }
            }
        }
    }

    override fun draw(
        canvas: Canvas?,
        currentMatrix: Matrix?,
        currentZoom: Float,
        currentRotateDegrees: Float
    ) {
        canvas!!.save()
        val goal = floatArrayOf(
            clickX,
            clickY
        )
        currentMatrix!!.mapPoints(goal)
        //mark ico
        canvas.drawBitmap(
            markBitmapMap[0]!!, goal[0] - markBitmapMap[0]!!.width / 2,
            goal[1] - markBitmapMap[0]!!.height / 2, paint
        )
        canvas.restore()
    }

    private fun createMarkDrawable() {
        markDrawableIdMap.forEach { (key, drawableId) ->
            val markDrawable = mapView!!.resources.getDrawable(drawableId, mapView!!.context.theme)
            markDrawable.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(
                    mapView!!.context,
                    R.color.md_theme_light_primary
                ), BlendModeCompat.SRC_ATOP
            )
            var bmpMark = Bitmap.createBitmap(
                markDrawable.intrinsicWidth,
                markDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val markCanvas = Canvas(bmpMark!!)
            markDrawable.copyBounds()
            markDrawable.setBounds(0, 0, markCanvas.width, markCanvas.height)
            markDrawable.draw(markCanvas)
            markBitmapMap.set(key, bmpMark!!)
        }
    }

    fun setMarkIsClickListener(listener: MarkIsClickListener?) {
        this.listener = listener
    }

    interface MarkIsClickListener {
        fun markIsClick(num: Int)
    }
}