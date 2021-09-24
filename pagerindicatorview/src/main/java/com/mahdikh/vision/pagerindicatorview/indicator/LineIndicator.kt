package com.mahdikh.vision.pagerindicatorview.indicator

import android.graphics.Canvas
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

open class LineIndicator : TransformIndicator() {
    override fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawLine(cx(baseInfo), info.y, cx(baseInfo), bottom(baseInfo), paint)
    }
}