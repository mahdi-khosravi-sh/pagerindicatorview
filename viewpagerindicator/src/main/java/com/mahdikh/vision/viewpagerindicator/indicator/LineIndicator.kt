package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.indicator.abstractions.TransformIndicator
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2

open class LineIndicator : TransformIndicator() {
    override fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawLine(cx(baseInfo), info.y, cx(baseInfo), bottom(baseInfo), paint)
    }
}