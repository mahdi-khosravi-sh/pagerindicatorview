package com.mahdikh.vision.pagerindicatorview.indicator

import android.graphics.Canvas
import android.graphics.Path
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

abstract class PathIndicator : TransformIndicator() {
    val path: Path = Path()

    override fun onReady() {
        super.onReady()
        adjustPath()
    }

    override fun onStructureChanged() {
        super.onStructureChanged()
        adjustPath()
    }

    open fun adjustPath() {
        if (!path.isEmpty) {
            path.reset()
        }
    }

    override fun onDrawing(canvas: Canvas, info: IndicatorInfo, paint: Paint2) {
        canvas.drawPath(path, paint)
    }
}