package com.mahdikh.vision.viewpagerindicator.progress

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class ScaleInstanceProgress : MultiIndicatorProgress() {
    override fun onDraw(canvas: Canvas, paint: Paint2) {
        canvas.save()
        val toSmallScale = 1.0F - fractionInterpolation()
        canvas.scale(toSmallScale, toSmallScale, cx(currentInfo), cy(currentInfo))
        indicator.onDraw(canvas, currentInfo, paint)
        canvas.restore()

        canvas.save()
        val toLargeScale = fractionInterpolation()
        canvas.scale(toLargeScale, toLargeScale, cx(destinationInfo), cy(destinationInfo))
        indicator.onDraw(canvas, destinationInfo, paint)
        canvas.restore()
    }
}