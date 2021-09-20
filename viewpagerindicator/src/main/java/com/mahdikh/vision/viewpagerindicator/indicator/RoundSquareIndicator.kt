package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class RoundSquareIndicator : Indicator() {
    var radius: Float = 5.0F

    override fun onDraw(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawRoundRect(
            info.x, info.y,
            right(info), bottom(info),
            radius, radius,
            paint
        )
    }
}