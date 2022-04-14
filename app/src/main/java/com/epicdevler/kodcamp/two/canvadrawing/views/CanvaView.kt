package com.epicdevler.kodcamp.two.canvadrawing.views

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.epicdevler.kodcamp.two.canvadrawing.R

class CanvaView constructor(context: Context) : View(context) {

    private lateinit var frame_inner: Rect
    private lateinit var frame_outter: Rect

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private var motionTouchEventX: Float? = 0f
    private var motionTouchEventY: Float? = 0f

    private val pressTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var currentX: Float = 0f
    private var currentY: Float = 0f


    private val STROKE_WIDTH = 12f
    private val bgColor = ResourcesCompat.getColor(resources, R.color.white, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.black, null)

    private val path = Path()
    private val drawing = Path()
    private val currentPath = Path()

    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraCanvas.drawColor(bgColor)

        val inset_outter = 50
        val inset_inner = 100
        frame_inner = Rect(inset_inner, inset_inner, w - inset_inner, h - inset_inner)
        frame_outter = Rect(inset_outter, inset_outter, w - inset_outter, h - inset_outter)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(drawing, paint)
        canvas?.drawPath(currentPath, paint)

//        ADD INSETS TO SCREENS
        canvas?.drawRect(frame_inner, paint)
        canvas?.drawRect(frame_outter, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        motionTouchEventX = event?.x
        motionTouchEventY = event?.y

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_UP -> touchUp()
            MotionEvent.ACTION_MOVE -> touchMove()
        }
        return true
    }

    private fun touchMove() {
        val dx = kotlin.math.abs(motionTouchEventX!! - currentX)
        val dy = kotlin.math.abs(motionTouchEventY!! - currentY)
        if (dx >= pressTolerance || dy >= pressTolerance) {
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX!! + currentX) / 2,
                (motionTouchEventY!! + currentY) / 2
            )
            currentX = motionTouchEventX!!
            currentY = motionTouchEventY!!
            extraCanvas.drawPath(path, paint)
        }
        invalidate()
    }

    private fun touchUp() {
        drawing.addPath(currentPath)
        currentPath.reset()
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX!!, motionTouchEventY!!)
        currentX = motionTouchEventX!!
        currentY = motionTouchEventY!!
    }

}












