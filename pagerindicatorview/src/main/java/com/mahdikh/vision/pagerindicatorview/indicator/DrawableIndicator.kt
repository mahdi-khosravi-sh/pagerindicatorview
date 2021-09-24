package com.mahdikh.vision.pagerindicatorview.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

open class DrawableIndicator : TransformIndicator {
    private var drawable: Drawable

    constructor(drawable: Drawable) : super() {
        this.drawable = drawable
    }

    constructor(context: Context, @DrawableRes resId: Int) : super() {
        drawable = ResourcesCompat.getDrawableForDensity(
            context.resources,
            resId,
            context.resources.displayMetrics.densityDpi,
            context.theme
        )!!
    }

    override fun onReady() {
        super.onReady()
        setDrawableBounds(baseInfo)
    }

    override fun onStructureChanged() {
        super.onStructureChanged()
        setDrawableBounds(baseInfo)
    }

    override fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        drawable.setTint(paint.color)
        drawable.draw(canvas)
        drawable.setTintList(null)
    }

    private fun setDrawableBounds(info: IndicatorInfo) {
        drawable.setBounds(
            info.x.toInt(),
            info.y.toInt(),
            right(info).toInt(),
            bottom(info).toInt()
        )
    }
}