package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class MoonIndicator : Indicator() {
    private val path = Path()
    private lateinit var info: IndicatorInfo

    override fun onReady() {
        adjustPath()
    }

    override fun onStructureChanged() {
        adjustPath()
    }

    private fun adjustPath() {
        path.reset()
        val size = size()

        info = pagerIndicator.getIndicatorInfo(0)
        path.arcTo(RectF(info.x, info.y, right(info), bottom(info)), 90F, 180F, true)

        val bottom = bottom(info)
        val xToLeft: Float = info.x + size * 0.3f
        val yToBottom: Float = info.y + size / 5f
        val yToTop: Float = bottom - size / 5f

        path.cubicTo(
            xToLeft, yToBottom,
            xToLeft, yToTop,
            info.x + size / 2f, bottom
        )
        path.close()
    }

    override fun onDraw(canvas: Canvas, position: Int, info: IndicatorInfo, paint: Paint2) {
        val halfSize = size() / 2F
        canvas.save()
        canvas.translate(info.x - this.info.x, 0.0F)
        canvas.rotate(-40f, this.info.x + halfSize, info.y + halfSize)
        canvas.drawPath(path, paint)
        canvas.restore()
    }
}