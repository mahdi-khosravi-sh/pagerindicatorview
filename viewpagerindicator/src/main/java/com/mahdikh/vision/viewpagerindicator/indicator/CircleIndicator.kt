package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2

open class CircleIndicator : Indicator() {
    override fun onDraw(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawCircle(cx(info), cy(info), computeRadius(), paint)
    }

    open fun computeRadius(): Float = size() / 2F
}