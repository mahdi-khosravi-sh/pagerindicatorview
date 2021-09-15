package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.indicator.abstractions.TransformIndicator
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2

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
        canvas.drawLine(baseInfo.x, cx, right(baseInfo), cy, paint)
        canvas.drawLine(cx, baseInfo.y, cx, bottom(baseInfo), paint)
    }
}