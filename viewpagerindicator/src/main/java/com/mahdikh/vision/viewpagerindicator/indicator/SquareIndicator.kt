package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2

open class SquareIndicator : Indicator() {
    override fun onDraw(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawRect(
            info.x, info.y,
            right(info), bottom(info),
            paint
        )
    }
}