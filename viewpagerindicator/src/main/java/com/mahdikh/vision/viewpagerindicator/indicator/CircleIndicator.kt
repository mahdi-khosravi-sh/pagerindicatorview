package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class CircleIndicator : Indicator() {
    override fun onDraw(canvas: Canvas, position: Int, info: IndicatorInfo, paint: Paint2) {
        canvas.drawCircle(cx(info), cy(info), computeRadius(), paint)
    }

    fun computeRadius(): Float = size() / 2F
}