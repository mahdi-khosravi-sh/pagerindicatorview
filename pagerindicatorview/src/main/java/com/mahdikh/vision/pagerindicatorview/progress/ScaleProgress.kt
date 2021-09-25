package com.mahdikh.vision.pagerindicatorview.progress

import android.graphics.Canvas
import android.graphics.Color
import androidx.viewpager.widget.ViewPager
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

class ScaleProgress : IndicatorProgress() {
    private var paintColor: Int = Color.TRANSPARENT
    var scale: Float = .6F
    var selectedScale: Float = 1.0F
    override var keepDraw: Boolean
        get() = true
        set(value) {}

    override fun onReady() {
        super.onReady()
        paintColor = getColor()
    }

    override fun onPreDraw(canvas: Canvas) {
        super.onPreDraw(canvas)
        syncIndicators(canvas)
    }

    override fun onDraw(canvas: Canvas, paint: Paint2) {
        val fraction = fractionInterpolation()
        val alpha1: Int = (255 * fraction).toInt()
        var scale1 = scale + (fraction * selectedScale)
        if (scale1 > selectedScale) {
            scale1 = selectedScale
        }

        paint.alpha = alpha1
        paint.color = computeColor(pagerIndicatorView.color, paintColor, fraction())
        val s2 = canvas.save()
        canvas.scale(scale1, scale1, cx(destinationInfo), cy(destinationInfo))
        indicator.onDraw(canvas, destinationInfo, paint)
        canvas.restoreToCount(s2)

        if (currentInfo != destinationInfo) {
            val alpha2: Int = (255 * (1 - fraction)).toInt()
            var scale2 = selectedScale - (fraction * scale1)
            if (scale2 < scale)
                scale2 = scale

            paint.alpha = alpha2
            paint.color = computeColor(paintColor, pagerIndicatorView.color, fraction())

            val s1 = canvas.save()
            canvas.scale(scale2, scale2, cx(currentInfo), cy(currentInfo))
            indicator.onDraw(canvas, currentInfo, paint)
            canvas.restoreToCount(s1)
        }
    }

    private fun syncIndicators(canvas: Canvas) {
        val infoList = pagerIndicatorView.getInfoList()
        val size = infoList.size
        var info: IndicatorInfo
        for (i in 0 until size) {
            info = infoList[i]
            info.draw = false
            canvas.save()
            canvas.scale(scale, scale, cx(info), cy(info))
            indicator.setColor(pagerIndicatorView.color)
            if (info != currentInfo && info != destinationInfo) {
                indicator.onDraw(canvas, info)
            }
            canvas.restore()
        }
    }

    override fun onScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            currentInfo = pagerIndicatorView.getIndicatorInfo(pagerIndicatorView.getCurrentItem())
            destinationInfo = currentInfo
        }
    }

    private fun computeColor(fromColor: Int, toColor: Int, fraction: Float): Int {
        val fA: Int = Color.alpha(fromColor)
        val fR: Int = Color.red(fromColor)
        val fG: Int = Color.green(fromColor)
        val fB: Int = Color.blue(fromColor)

        val tA: Int = Color.alpha(toColor)
        val tR: Int = Color.red(toColor)
        val tG: Int = Color.green(toColor)
        val tB: Int = Color.blue(toColor)

        val a: Int = (fA + (tA - fA) * fraction).toInt()
        val r: Int = (fR + (tR - fR) * fraction).toInt()
        val g: Int = (fG + (tG - fG) * fraction).toInt()
        val b: Int = (fB + (tB - fB) * fraction).toInt()

        return Color.argb(a, r, g, b)
    }
}