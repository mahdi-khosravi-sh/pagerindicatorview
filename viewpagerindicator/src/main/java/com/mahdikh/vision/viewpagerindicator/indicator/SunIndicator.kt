package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.LoopDraw
import com.mahdikh.vision.viewpagerindicator.indicator.abstractions.TransformIndicator
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class SunIndicator : TransformIndicator(), LoopDraw {
    override var loopCount: Int = 5
    var factor: Float = 0.5F

    private var cx: Float = 0.0F
    private var cy: Float = 0.0F
    private var startY: Float = 0.0F

    override fun onReady() {
        super.onReady()
        setCoordinates()
    }

    override fun onStructureChanged() {
        super.onStructureChanged()
        setCoordinates()
    }

    private fun setCoordinates() {
        cx = cx(baseInfo)
        cy = cy(baseInfo)
        startY = cy - computeRadius()
    }

    override fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawCircle(cx, cy, computeRadius(), paint)
        loopDraw(canvas, cx, cy) {
            canvas.drawLine(cx, startY, cx, info.y, paint)
        }
    }

    fun computeRadius(): Float {
        return size() / 2F * (1 - factor)
    }
}