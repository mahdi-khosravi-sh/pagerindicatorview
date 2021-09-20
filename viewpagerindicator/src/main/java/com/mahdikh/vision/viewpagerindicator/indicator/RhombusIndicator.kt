package com.mahdikh.vision.viewpagerindicator.indicator

class RhombusIndicator : PathIndicator() {
   override fun adjustPath() {
       super.adjustPath()
        path.moveTo(cx(baseInfo), baseInfo.y)
        path.lineTo(right(baseInfo), cy(baseInfo))
        path.lineTo(cx(baseInfo), bottom(baseInfo))
        path.lineTo(baseInfo.x, cy(baseInfo))
        path.close()
    }
}