package com.mahdikh.vision.viewpagerindicator.indicator

import android.graphics.Canvas
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2
import com.mahdikh.vision.viewpagerindicator.widget.PagerIndicator

abstract class Indicator {
    internal open lateinit var pagerIndicator: PagerIndicator

    fun cx(info: IndicatorInfo): Float = info.x + pagerIndicator.indicatorSize / 2F

    fun cy(info: IndicatorInfo): Float = info.y + pagerIndicator.indicatorSize / 2F

    fun cx(position: Int): Float = cx(pagerIndicator.getIndicatorInfo(position))

    fun cy(position: Int): Float = cy(pagerIndicator.getIndicatorInfo(position))

    fun right(info: IndicatorInfo): Float = info.x + pagerIndicator.indicatorSize

    fun bottom(info: IndicatorInfo): Float = info.y + pagerIndicator.indicatorSize

    fun right(position: Int): Float = right(pagerIndicator.getIndicatorInfo(position))

    fun bottom(position: Int): Float = bottom(pagerIndicator.getIndicatorInfo(position))

    fun size(): Int = pagerIndicator.indicatorSize

    open fun onReady() {
    }

    open fun onStructureChanged() {
    }

    abstract fun onDraw(canvas: Canvas, position: Int, info: IndicatorInfo, paint: Paint2)
}