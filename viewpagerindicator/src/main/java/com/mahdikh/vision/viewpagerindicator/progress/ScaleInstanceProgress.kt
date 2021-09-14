package com.mahdikh.vision.viewpagerindicator.progress

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class ScaleInstanceProgress : IndicatorProgress() {
    override fun onDraw(canvas: Canvas, paint: Paint2) {
        canvas.rotate(0F)
        canvas.save()

        val toSmallScale = 1.0F - fraction
        canvas.scale(toSmallScale, toSmallScale, cx(currentInfo), cy(currentInfo))
        indicator.onDraw(canvas, 0, currentInfo, paint)
        canvas.restore()

        canvas.save()
        val toLargeScale = fraction
        canvas.scale(toLargeScale, toLargeScale, cx(destinationInfo), cy(destinationInfo))
        indicator.onDraw(canvas, 0, destinationInfo, paint)
        canvas.restore()
    }
}