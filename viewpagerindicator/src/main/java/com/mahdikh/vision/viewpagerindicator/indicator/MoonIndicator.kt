package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.RectF

class MoonIndicator : PathIndicator() {
    override var rotation: Float = -40.0F

    override fun adjustPath() {
        path.reset()
        val size = size()

        path.arcTo(
            RectF(baseInfo.x, baseInfo.y, right(baseInfo), bottom(baseInfo)),
            90F, 180F, true
        )

        val bottom = bottom(baseInfo)
        val xToLeft: Float = baseInfo.x + size * 0.3f
        val yToBottom: Float = baseInfo.y + size / 5f
        val yToTop: Float = bottom - size / 5f

        path.cubicTo(
            xToLeft, yToBottom,
            xToLeft, yToTop,
            baseInfo.x + size / 2f, bottom
        )
        path.close()
    }
}