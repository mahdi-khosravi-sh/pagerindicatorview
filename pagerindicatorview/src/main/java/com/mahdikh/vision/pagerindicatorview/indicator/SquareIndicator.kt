package com.mahdikh.vision.pagerindicatorview.indicator

import android.graphics.Canvas
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

open class SquareIndicator : TransformIndicator() {
    var cornerRadius: Float = 0.0F
    private var right = 0.0F
    private var bottom = 0.0F

    override fun onReady() {
        super.onReady()
        setCoordinates()
    }

    override fun onStructureChanged() {
        super.onStructureChanged()
        setCoordinates()
    }

    private fun setCoordinates() {
        right = right(baseInfo)
        bottom = bottom(baseInfo)
    }

    override fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawRoundRect(
            baseInfo.x, baseInfo.y,
            right, bottom,
            cornerRadius, cornerRadius,
            paint
        )
    }
}