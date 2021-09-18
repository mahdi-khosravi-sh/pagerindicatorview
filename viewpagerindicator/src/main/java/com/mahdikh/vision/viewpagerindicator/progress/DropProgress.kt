package com.mahdikh.vision.viewpagerindicator.progress

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.util.Paint2
import kotlin.math.abs

class DropProgress : MultiIndicatorProgress() {
    var jumpFactor: Float = 1.0F

    override fun onDraw(canvas: Canvas, paint: Paint2) {
        val fraction = fractionInterpolation()
        val distance = computeDistance()

        val y = abs(
            if (fraction < 0.5F) {
                -distance * fraction
            } else {
                -distance * (1 - fraction)
            }
        ) * jumpFactor

        val scale =
            if (fraction < 0.5F) {
                1 * (1 - fraction)
            } else {
                1 * fraction
            }


        canvas.save()
        canvas.scale(scale, scale, cx(currentInfo) + distance / 2F, cy(destinationInfo))
        canvas.translate(distance * fraction, -y)
        indicator.onDraw(canvas, currentInfo, paint)
        canvas.restore()
    }
}