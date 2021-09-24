package com.mahdikh.vision.pagerindicatorview.indicator

import android.graphics.Canvas
import androidx.annotation.CallSuper
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

abstract class TransformIndicator : Indicator() {
    protected lateinit var baseInfo: IndicatorInfo
    open var rotation: Float = 0.0F

    @CallSuper
    override fun onReady() {
        setBaseInfo()
    }

    @CallSuper
    override fun onStructureChanged() {
        setBaseInfo()
    }

    private fun setBaseInfo() {
        baseInfo = pagerIndicatorView.getIndicatorInfo(0)
    }

    open fun onBeforeDraw(canvas: Canvas, info: IndicatorInfo) {
        canvas.save()
        canvas.translate(info.x - baseInfo.x, 0.0F)
        if (rotation != 0.0F) {
            canvas.rotate(rotation, cx(baseInfo), cy(baseInfo))
        }
    }

    final override fun onDraw(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        onBeforeDraw(canvas, info)
        onDrawing(canvas, info, paint)
        onAfterDraw(canvas)
    }

    open fun onAfterDraw(canvas: Canvas) {
        canvas.restore()
    }

    abstract fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2)
}