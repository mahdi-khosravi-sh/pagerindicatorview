package com.mahdikh.vision.pagerindicatorview.indicator

import android.graphics.Canvas
import android.graphics.Paint
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2
import com.mahdikh.vision.pagerindicatorview.widget.PagerIndicatorView

abstract class Indicator {
    internal open lateinit var pagerIndicatorView: PagerIndicatorView
    protected val paint: Paint2 = Paint2()

    fun setColor(color: Int) {
        paint.color = color
    }

    fun setStrokeWidth(strokeWidth: Float) {
        paint.strokeWidth = strokeWidth
    }

    fun setStyle(style: Paint.Style) {
        paint.style = style
    }

    fun setStyle(style: Int) {
        paint.setStyle(style)
    }

    fun getStrokeWidth(): Float {
        return paint.strokeWidth
    }

    fun cx(info: IndicatorInfo): Float = info.x + pagerIndicatorView.indicatorSize / 2F

    fun cy(info: IndicatorInfo): Float = info.y + pagerIndicatorView.indicatorSize / 2F

    fun cx(position: Int): Float = cx(pagerIndicatorView.getIndicatorInfo(position))

    fun cy(position: Int): Float = cy(pagerIndicatorView.getIndicatorInfo(position))

    fun right(info: IndicatorInfo): Float = info.x + pagerIndicatorView.indicatorSize

    fun bottom(info: IndicatorInfo): Float = info.y + pagerIndicatorView.indicatorSize

    fun right(position: Int): Float = right(pagerIndicatorView.getIndicatorInfo(position))

    fun bottom(position: Int): Float = bottom(pagerIndicatorView.getIndicatorInfo(position))

    fun size(): Int = pagerIndicatorView.indicatorSize

    open fun onReady() {
    }

    open fun onStructureChanged() {
    }

    abstract fun onDraw(canvas: Canvas, info: IndicatorInfo, paint: Paint2)

    fun onDraw(canvas: Canvas, info: IndicatorInfo) {
        onDraw(canvas, info, paint)
    }
}