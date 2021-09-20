package com.mahdikh.vision.viewpagerindicator.progress

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Canvas
import androidx.viewpager.widget.ViewPager
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class WormProgress : IndicatorProgress() {
    private lateinit var oldCurrent: IndicatorInfo
    private lateinit var oldDestination: IndicatorInfo
    private var animator: ValueAnimator? = null
    private var fromAnimator = false
    private var currentPage: Int = 0
    private var animatedX: Float = 0.0F
    var duration: Long = 200

    override fun onReady() {
        super.onReady()
        oldCurrent = currentInfo
        oldDestination = currentInfo
        setStrokeWidth(indicator.size().toFloat())
    }

    override fun onDraw(canvas: Canvas, paint: Paint2) {
        val cx = cx(currentInfo)
        if (!fromAnimator) {
            canvas.drawLine(
                cx,
                cy(currentInfo),
                cx + computeDistance() * fractionInterpolation(),
                cy(destinationInfo),
                paint
            )
        } else {
            canvas.drawLine(animatedX, cy(currentInfo), cx, cy(currentInfo), paint)
        }
    }

    override fun onIndicatorInfoChanged(oldCurrent: IndicatorInfo, oldDestination: IndicatorInfo) {
        super.onIndicatorInfoChanged(oldCurrent, oldDestination)
        this.oldCurrent = oldCurrent
        this.oldDestination = oldDestination
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        animator?.let {
            it.end()
            fromAnimator = false
            it.removeAllListeners()
            it.removeAllUpdateListeners()
            animator = null
            pagerIndicator.invalidate()
        }
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (currentPage == pagerIndicator.getCurrentItem()) {
                oldCurrent = currentInfo
                oldDestination = currentInfo
                destinationInfo = currentInfo
                return
            }
            currentPage = pagerIndicator.getCurrentItem()
            animator = ValueAnimator.ofFloat(cx(oldDestination), cx(currentInfo)).apply {
                duration = this@WormProgress.duration
                interpolator = this@WormProgress.interpolator
                addUpdateListener {
                    animatedX = it.animatedValue as Float
                    pagerIndicator.invalidate()
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        inProgress = true
                        fromAnimator = true
                        oldDestination = currentInfo
                        oldCurrent = currentInfo
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        fromAnimator = false
                        removeAllListeners()
                        removeAllUpdateListeners()
                        animator = null
                        inProgress = false
                    }
                })
                start()
            }
        }
    }
}