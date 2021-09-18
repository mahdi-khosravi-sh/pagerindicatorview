package com.mahdikh.vision.viewpagerindicator.progress

import androidx.viewpager.widget.ViewPager
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo

abstract class MultiIndicatorProgress : IndicatorProgress() {
    override fun onReady() {
        super.onReady()
        if (keepDraw) {
            pagerIndicator.getInfoList()[pagerIndicator.getCurrentItem()].draw = false
        }
    }

    override fun onIndicatorInfoChanged(
        oldCurrent: IndicatorInfo,
        oldDestination: IndicatorInfo
    ) {
        oldCurrent.draw = true
        oldDestination.draw = true
    }

    override fun onPageScrollStateChanged(state: Int) {
        currentInfo.draw = true
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            currentInfo = pagerIndicator.getIndicatorInfo(pagerIndicator.getCurrentItem())
            destinationInfo = currentInfo
        }
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (keepDraw) {
                currentInfo.draw = false
            }
        }
        super.onPageScrollStateChanged(state)
    }
}