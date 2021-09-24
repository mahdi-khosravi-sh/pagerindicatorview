package com.mahdikh.vision.pagerindicatorview.indicator

import android.graphics.Canvas
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

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