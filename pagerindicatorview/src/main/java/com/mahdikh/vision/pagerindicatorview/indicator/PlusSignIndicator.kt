package com.mahdikh.vision.pagerindicatorview.indicator

import android.graphics.Canvas
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

class PlusSignIndicator : TransformIndicator() {
    private var cx = 0.0F
    private var cy = 0.0F

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
    }

    override fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawLine(baseInfo.x, cy, right(baseInfo), cy, paint)
        canvas.drawLine(cx, baseInfo.y, cx, bottom(baseInfo), paint)
    }
}