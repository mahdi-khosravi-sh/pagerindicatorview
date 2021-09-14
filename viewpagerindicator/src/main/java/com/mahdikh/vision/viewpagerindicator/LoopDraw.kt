package com.mahdikh.vision.viewpagerindicator

import android.graphics.Canvas
import androidx.annotation.CallSuper

interface LoopDraw {
    var loopCount: Int

    @CallSuper
    fun loopDraw(canvas: Canvas, px: Float, py: Float, draw: Runnable) {
        val count = loopCount
        val degrees = 360.0F / count
        for (i in 1..count) {
            canvas.save()
            canvas.rotate(i * degrees, px, py)
            draw.run()
            canvas.restore()
        }
    }
}