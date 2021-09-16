package com.mahdikh.vision.viewpagerindicator.progress

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.util.Paint2

open class InstanceProgress : IndicatorProgress() {

    override fun onDraw(canvas: Canvas, paint: Paint2) {
        canvas.save()
        canvas.translate(computeWidth() * fractionInterpolation(), 0.0F)
        indicator.onDraw(canvas, currentInfo, paint)
        canvas.restore()
    }
}