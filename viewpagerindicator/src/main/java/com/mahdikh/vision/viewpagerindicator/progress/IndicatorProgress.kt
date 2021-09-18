package com.mahdikh.vision.viewpagerindicator.progress

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Color
import androidx.annotation.CallSuper
import androidx.viewpager.widget.ViewPager
import com.mahdikh.vision.viewpagerindicator.indicator.abstractions.Indicator
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2
import com.mahdikh.vision.viewpagerindicator.widget.PagerIndicator

abstract class IndicatorProgress {
    private val paint: Paint2 = Paint2().also {
        it.color = Color.parseColor("#ff5555")
    }
    protected open lateinit var pagerIndicator: PagerIndicator
    protected lateinit var indicator: Indicator
    protected lateinit var currentInfo: IndicatorInfo
    protected lateinit var destinationInfo: IndicatorInfo
    private var oldDestinationPosition = 0
    var interpolator: TimeInterpolator? = null
    open var keepDraw = false
    var inProgress: Boolean = false
        protected set
    var offset: Float = 0.0F
        private set

    open fun setStrokeWidth(strokeWidth: Float) {
        paint.strokeWidth = strokeWidth
    }

    open fun getColor(): Int {
        return paint.color
    }

    @CallSuper
    open fun onPageScrolled(position: Int, offset: Float, state: Int) {
        val currentPosition = pagerIndicator.getCurrentItem()
        var destinationPosition: Int

        destinationPosition = if (currentPosition == position) {
            if (offset <= 0.005f) {
                currentPosition
            } else {
                currentPosition + 1
            }
        } else {

            currentPosition - 1
        }

        if (destinationPosition <= 0) destinationPosition = 0

        if (destinationPosition != oldDestinationPosition && state != ViewPager.SCROLL_STATE_SETTLING) {
            val oldCurrent = currentInfo
            val oldDestination = destinationInfo

            currentInfo = pagerIndicator.getIndicatorInfo(currentPosition)
            destinationInfo = pagerIndicator.getIndicatorInfo(destinationPosition)

            onIndicatorInfoChanged(oldCurrent, oldDestination)
        }
        this.offset = offset
        oldDestinationPosition = destinationPosition
        pagerIndicator.invalidate()
    }

    @CallSuper
    open fun onPageScrollStateChanged(state: Int) {
        inProgress = state != ViewPager.SCROLL_STATE_IDLE
//        if (state == ViewPager.SCROLL_STATE_IDLE) {
//            currentInfo = pagerIndicator.getIndicatorInfo(pagerIndicator.getCurrentItem())
//            destinationInfo = currentInfo
//        }
        pagerIndicator.invalidate()
    }

    internal fun setIndicator(indicator: Indicator) {
        this.indicator = indicator
    }

    internal fun setPagerIndicator(pagerIndicator: PagerIndicator) {
        this.pagerIndicator = pagerIndicator
    }

    @CallSuper
    open fun onReady() {
        currentInfo = pagerIndicator.getIndicatorInfo(pagerIndicator.getCurrentItem())
        destinationInfo = currentInfo
    }

    open fun draw(canvas: Canvas) {
        if (inProgress || keepDraw) {
            onDraw(canvas, paint)
        }
    }

    open fun onIndicatorInfoChanged(
        oldCurrent: IndicatorInfo,
        oldDestination: IndicatorInfo
    ) {
        // Do Nothing
    }


    open fun onPreDraw(canvas: Canvas) {
        // Do Nothing
    }

    abstract fun onDraw(canvas: Canvas, paint: Paint2)

    protected fun cx(info: IndicatorInfo): Float = indicator.cx(info)

    protected fun cy(info: IndicatorInfo): Float = indicator.cy(info)

    fun computeDistance(): Float = cx(destinationInfo) - cx(currentInfo)

    open fun getInterpolation(input: Float): Float {
        return interpolator?.getInterpolation(input) ?: input
    }

    open fun fractionInterpolation(): Float = getInterpolation(fraction())

    open fun fraction(): Float {
        return if (computeDistance() > 0) offset else 1.0F - offset
    }

    open fun offsetInterpolation(): Float = getInterpolation(offset)
}