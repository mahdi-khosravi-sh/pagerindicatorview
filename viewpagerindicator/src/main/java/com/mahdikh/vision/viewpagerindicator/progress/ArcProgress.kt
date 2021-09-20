package com.mahdikh.vision.viewpagerindicator.progress

import android.graphics.Canvas
import android.graphics.RectF
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class ArcProgress : IndicatorProgress() {
    private val rectF = RectF()
    var startAngle = -90.0F

    override fun onDraw(canvas: Canvas, paint: Paint2) {
        setRectF(cx(currentInfo), cy(currentInfo))
        val fraction = fractionInterpolation()
        canvas.drawArc(rectF, startAngle, 360 * (1.0F - fraction), true, paint)

        setRectF(cx(destinationInfo), cy(destinationInfo))
        canvas.drawArc(rectF, startAngle, 360 * fraction, true, paint)
    }

    private fun setRectF(cx: Float, cy: Float) {
        val size = indicator.size() / 2F
        rectF.set(cx - size, cy - size, cx + size, cy + size)
    }
}