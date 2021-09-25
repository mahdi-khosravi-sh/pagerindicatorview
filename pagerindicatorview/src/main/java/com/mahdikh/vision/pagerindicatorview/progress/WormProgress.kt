package com.mahdikh.vision.pagerindicatorview.progress

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.graphics.Canvas
import androidx.viewpager.widget.ViewPager
import com.mahdikh.vision.pagerindicatorview.info.IndicatorInfo
import com.mahdikh.vision.pagerindicatorview.util.Paint2

class WormProgress : IndicatorProgress() {
    private var animator: ValueAnimator = ValueAnimator()
    private var fromAnimator = false
    private var currentPage: Int = 0
    private var animatedX: Float = 0.0F
    private lateinit var oldCurrent: IndicatorInfo
    private lateinit var oldDestination: IndicatorInfo

    override var interpolator: TimeInterpolator?
        get() = super.interpolator
        set(value) {
            super.interpolator = value
            animator.interpolator = value
        }

    var duration: Long = 200
        set(value) {
            field = value
            animator.duration = field
        }

    init {
        animator.duration = duration
        animator.addUpdateListener {
            animatedX = it.animatedValue as Float
            pagerIndicatorView.invalidate()
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                inProgress = true
                fromAnimator = true
                oldDestination = currentInfo
                oldCurrent = currentInfo
            }

            override fun onAnimationEnd(animation: Animator?) {
                fromAnimator = false
                inProgress = false
            }
        })
    }

    override fun onReady() {
        super.onReady()
        oldCurrent = currentInfo
        oldDestination = currentInfo
        setStrokeWidth(indicator.size().toFloat())
    }

    override fun onIndicatorInfoChanged(oldCurrent: IndicatorInfo, oldDestination: IndicatorInfo) {
        super.onIndicatorInfoChanged(oldCurrent, oldDestination)
        this.oldCurrent = oldCurrent
        this.oldDestination = oldDestination
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (animator.isRunning) {
            animator.end()
        }
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (currentPage == pagerIndicatorView.getCurrentItem()) {
                oldCurrent = currentInfo
                oldDestination = currentInfo
                destinationInfo = currentInfo
                return
            }
            currentPage = pagerIndicatorView.getCurrentItem()
            animator.setFloatValues(cx(oldDestination), cx(currentInfo))
            animator.start()
        }
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
}