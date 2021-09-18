package com.mahdikh.vision.viewpagerindicator.util

import android.graphics.Paint

class Paint2 : Paint(ANTI_ALIAS_FLAG) {
    companion object {
        @JvmStatic
        fun getStyle(index: Int): Style? = when (index) {
            0 -> Style.FILL
            1 -> Style.STROKE
            2 -> Style.FILL_AND_STROKE
            else -> null
        }
    }

    fun setStyle(index: Int) {
        style = getStyle(index)
    }

    init {
        strokeCap = Cap.ROUND
        strokeJoin = Join.ROUND
    }
}