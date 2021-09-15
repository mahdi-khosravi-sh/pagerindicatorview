package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.LoopDraw
import com.mahdikh.vision.viewpagerindicator.indicator.abstractions.PathIndicator
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2
import kotlin.math.PI

open class FlowerIndicator : PathIndicator(), LoopDraw {
    override var loopCount: Int = 3
    var factor: Float = 0.8F
    var circleRadiusFactor: Float = 1.2F

    private var cy: Float = 0.0F
    private var cx: Float = 0.0F

    override fun adjustPath() {
        super.adjustPath()
        val leafWidth: Float = perimeter() / loopCount

        cy = cy(baseInfo)
        cx = cy(baseInfo)

        val radius: Float = radius()
        val pointBottom: Float = cy - radius

        var pointLeftX: Float = cx - leafWidth / 2f
        var pointRightX = pointLeftX + leafWidth

        val left: Float = cx - radius
        if (pointLeftX < left) {
            pointLeftX = left
        }
        val right: Float = cx + radius
        if (pointRightX > right) {
            pointRightX = right
        }

        path.moveTo(pointLeftX, pointBottom)
        path.cubicTo(
            pointLeftX, baseInfo.y,
            pointRightX, baseInfo.y,
            pointRightX, pointBottom
        )
    }

    override fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawCircle(cx, cy, radius() * circleRadiusFactor, paint)

        loopDraw(canvas, cx, cy) {
            canvas.drawPath(path, paint)
        }
    }

    open fun perimeter(): Float = (2 * PI * radius()).toFloat()

    open fun radius(): Float = size() / 2 * (1 - factor)
}