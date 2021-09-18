package com.mahdikh.vision.viewpagerindicator.progress

import android.graphics.Canvas
import androidx.viewpager.widget.ViewPager
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2
import kotlin.math.abs

class SwapProgress : MultiIndicatorProgress() {
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

        if (currentInfo != destinationInfo) {
            canvas.save()
            canvas.translate(-distance * fraction, y)
            indicator.onDraw(canvas, destinationInfo)
            canvas.restore()
        }

        canvas.save()
        canvas.translate(distance * fraction, -y)
        indicator.onDraw(canvas, currentInfo, paint)
        canvas.restore()
    }

    override fun onIndicatorInfoChanged(oldCurrent: IndicatorInfo, oldDestination: IndicatorInfo) {
        super.onIndicatorInfoChanged(oldCurrent, oldDestination)
        setIndicatorInfoDraw(false)
    }

    override fun onPageScrollStateChanged(state: Int) {
        destinationInfo.draw = true
        super.onPageScrollStateChanged(state)
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            setIndicatorInfoDraw(true)
            if (keepDraw) {
                currentInfo.draw = false
            }
        } else {
            setIndicatorInfoDraw(false)
        }
        pagerIndicator.invalidate()
    }

    private fun setIndicatorInfoDraw(draw: Boolean) {
        currentInfo.draw = draw
        destinationInfo.draw = draw
    }
}