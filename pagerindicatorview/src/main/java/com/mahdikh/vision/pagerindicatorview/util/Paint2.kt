package com.mahdikh.vision.pagerindicatorview.util

import android.graphics.Paint

class Paint2 : Paint(ANTI_ALIAS_FLAG) {
    companion object {
        @JvmStatic
        fun getStyle(index: Int): Style = when (index) {
            0 -> Style.FILL
            1 -> Style.STROKE
            else -> Style.FILL_AND_STROKE
        }
    }

    init {
        strokeCap = Cap.ROUND
        strokeJoin = Join.ROUND
    }
}