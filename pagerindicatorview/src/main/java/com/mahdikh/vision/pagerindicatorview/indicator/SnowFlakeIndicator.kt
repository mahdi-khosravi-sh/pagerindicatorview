package com.mahdikh.vision.pagerindicatorview.indicator

import android.graphics.Canvas
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

class SnowFlakeIndicator : TransformIndicator() {
    var branchCounts: Int = 5
    var factor: Float = 0.3F

    private var cx: Float = 0.0F
    private var cy: Float = 0.0F
    private var topPoint: Float = 0.0F
    private var diameter: Float = 0.0F
    private var radius: Float = 0.0F

    override fun onReady() {
        super.onReady()
        setCoordinates()
    }

    override fun onStructureChanged() {
        super.onStructureChanged()
        setCoordinates()
    }

    private fun setCoordinates() {
        radius = computeCircleRadius()
        cx = cx(baseInfo)
        cy = cy(baseInfo)
        topPoint = baseInfo.y + radius
        diameter = baseInfo.y + radius * 2
    }

    override fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        val count = branchCounts
        val degrees = 360.0F / count
        for (i in 1..count) {
            canvas.save()
            canvas.rotate(i * degrees, cx, cy)
            canvas.drawLine(cx, cy, cx, diameter, paint)
            canvas.drawCircle(cx, topPoint, radius, paint)
            canvas.restore()
        }
    }

    private fun computeCircleRadius(): Float = factor * (size() / 2F)
}