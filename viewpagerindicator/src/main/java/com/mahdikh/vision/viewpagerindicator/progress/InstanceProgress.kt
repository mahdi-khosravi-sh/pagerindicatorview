package com.mahdikh.vision.viewpagerindicator.progress

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.view.animation.LinearInterpolator
import com.mahdikh.vision.viewpagerindicator.util.Paint2

open class InstanceProgress : IndicatorProgress() {
    private val interpolator:TimeInterpolator = LinearInterpolator()

    override fun onDraw(canvas: Canvas, paint: Paint2) {
        canvas.save()
        canvas.translate(computeWidth() * fraction, 0.0F)

        canvas.rotate(interpolator.getInterpolation(offset) * 360.0F  , cx(currentInfo), cy(currentInfo))
        indicator.onDraw(canvas, currentInfo, paint)

        canvas.restore()
    }
}