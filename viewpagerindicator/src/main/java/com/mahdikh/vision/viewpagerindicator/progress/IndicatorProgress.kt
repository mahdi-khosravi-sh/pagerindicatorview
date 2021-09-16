package com.mahdikh.vision.viewpagerindicator.progress

import android.graphics.Canvas
import android.graphics.Color
import androidx.viewpager.widget.ViewPager
import com.mahdikh.vision.viewpagerindicator.indicator.abstractions.Indicator
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2
import com.mahdikh.vision.viewpagerindicator.widget.PagerIndicator

abstract class IndicatorProgress {
    private val paint: Paint2 = Paint2().also {
        it.color = Color.parseColor("#FF5555")
    }
    protected open lateinit var pagerIndicator: PagerIndicator
    protected lateinit var indicator: Indicator
    protected lateinit var currentInfo: IndicatorInfo
    protected lateinit var destinationInfo: IndicatorInfo
    private var oldDestinationPosition = 0
    var inProgress: Boolean = false
        private set
    var keepDraw = false
    var fraction: Float = 0.0F
        get() = if (computeWidth() > 0) field else 1.0F - field

    var offset: Float = 0.0F

    fun setStrokeWidth(strokeWidth: Float) {
        paint.strokeWidth = strokeWidth
    }

    fun onPageScrolled(position: Int, offset: Float) {
        this.offset = offset
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

        if (destinationPosition != oldDestinationPosition) {
            currentInfo = pagerIndicator.getIndicatorInfo(currentPosition)
            destinationInfo = pagerIndicator.getIndicatorInfo(destinationPosition)
        }

        fraction = offset
        oldDestinationPosition = destinationPosition
        pagerIndicator.invalidate()
    }

    fun onPageScrollStateChanged(state: Int) {
        inProgress = (state == ViewPager.SCROLL_STATE_DRAGGING
                || state == ViewPager.SCROLL_STATE_SETTLING)
        pagerIndicator.invalidate()
    }

    fun computeWidth(): Float {
        return cx(destinationInfo) - cx(currentInfo)
    }

    open fun virtualScroll() {
        onPageScrolled(pagerIndicator.getCurrentItem(), 1.0f)
    }

    protected fun cx(info: IndicatorInfo): Float = indicator.cx(info)

    protected fun cy(info: IndicatorInfo): Float = indicator.cy(info)

    internal fun setIndicator(indicator: Indicator) {
        this.indicator = indicator
    }

    internal fun setPagerIndicator(pagerIndicator: PagerIndicator) {
        this.pagerIndicator = pagerIndicator
    }

    open fun onReady() {
        currentInfo = pagerIndicator.getIndicatorInfo(pagerIndicator.getCurrentItem())
        destinationInfo = currentInfo
    }

    abstract fun onDraw(canvas: Canvas, paint: Paint2)

    open fun draw(canvas: Canvas) {
        if (inProgress || keepDraw) {
            onDraw(canvas, paint)
        }
    }
}