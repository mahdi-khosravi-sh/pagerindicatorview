package com.mahdikh.vision.viewpagerindicator.progress

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class InstanceProgress : IndicatorProgress() {
    override fun onDraw(canvas: Canvas, paint: Paint2) {
        canvas.save()
        canvas.translate(computeWidth() * fraction, 0.0F)
        indicator.onDraw(canvas, 0, currentInfo, paint)
        canvas.restore()
    }
}