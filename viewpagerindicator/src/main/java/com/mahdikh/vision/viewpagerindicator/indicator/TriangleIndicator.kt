package com.mahdikh.vision.viewpagerindicator.indicator

import kotlin.math.sqrt

class TriangleIndicator : PathIndicator() {
    override fun adjustPath() {
        super.adjustPath()
        var bottom: Float = bottom(baseInfo)
        var pointTopY: Float = bottom - size() / 2f * sqrt(3.0).toFloat()

        val diff = pointTopY / 2f
        pointTopY -= diff
        bottom -= diff
        path.moveTo(cx(baseInfo), pointTopY)
        path.lineTo(right(baseInfo), bottom)
        path.lineTo(baseInfo.x, bottom)
        path.close()
    }
}