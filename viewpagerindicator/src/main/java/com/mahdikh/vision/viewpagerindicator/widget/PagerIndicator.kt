package com.mahdikh.vision.viewpagerindicator.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.mahdikh.vision.viewpagerindicator.R
import com.mahdikh.vision.viewpagerindicator.indicator.CircleIndicator
import com.mahdikh.vision.viewpagerindicator.indicator.Indicator
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.progress.IndicatorProgress
import com.mahdikh.vision.viewpagerindicator.util.Paint2

class PagerIndicator : View {
    private val infoList: MutableList<IndicatorInfo> = mutableListOf()
    private val paint: Paint2 = Paint2()
    private lateinit var pager: Pager
    var setupWithViewPager = false
        private set
    var progress: IndicatorProgress? = null
        set(value) {
            field = value
            field?.let {
                it.setPagerIndicator(this)
                it.setIndicator(indicator)
            }
        }
    var indicatorSize: Int = 25
        set(value) {
            field = value
            if (setupWithViewPager) {
                remeasuringIndicators()
            }
        }
    var indicatorSpace: Int = 10
        set(value) {
            field = value
            if (setupWithViewPager) {
                remeasuringIndicators()
            }
        }
    var color: Int = Color.LTGRAY
        set(value) {
            field = value
            if (setupWithViewPager) {
                invalidate()
            }
        }
    var selectedColor: Int = Color.BLACK
        set(value) {
            field = value
            if (setupWithViewPager) {
                invalidate()
            }
        }
    var indicator: Indicator = CircleIndicator()
        set(value) {
            field = value
            field.pagerIndicator = this
            progress?.setIndicator(field)
        }

    init {
        indicator.pagerIndicator = this
    }

    private val onPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                progress?.onPageScrolled(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {
                invalidate()
            }

            override fun onPageScrollStateChanged(state: Int) {
                progress?.onPageScrollStateChanged(state)
            }
        }

    constructor(context: Context) : super(context) {
        initialization(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialization(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialization(context, attrs, defStyleAttr)
    }

    private fun initialization(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null) {
            val a: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.PagerIndicator,
                defStyleAttr,
                0
            )

            val size = a.indexCount
            var index: Int

            for (i in 0 until size) {
                index = a.getIndex(i)
                when (index) {
                    R.styleable.PagerIndicator_color -> {
                        color = a.getColor(index, Color.LTGRAY)
                    }
                    R.styleable.PagerIndicator_selectedColor -> {
                        selectedColor = a.getColor(index, Color.BLACK)
                    }
                    R.styleable.PagerIndicator_indicatorSpace -> {
                        indicatorSpace = a.getDimensionPixelSize(index, 10)
                    }
                    R.styleable.PagerIndicator_indicatorSize -> {
                        indicatorSize = a.getDimensionPixelSize(index, 25)
                    }
                    R.styleable.PagerIndicator_indicatorStrokeWidth -> {
                        paint.strokeWidth = a.getDimension(index, 0.0F)
                    }
                    R.styleable.PagerIndicator_indicatorStyle -> {
                        paint.setStyle(a.getInt(index, 0))
                    }
                }
            }
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(0, 0)
    }

    internal fun getIndicatorInfo(position: Int): IndicatorInfo {
        return infoList[position]
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (setupWithViewPager) {
            val size = infoList.size
            val current = getCurrentItem()
            for (i in 0 until size) {
                if (i == current && progress?.let { !it.inProgress } != false) {
                    paint.color = selectedColor
                } else {
                    paint.color = color
                }
                indicator.onDraw(canvas, i, infoList[i], paint)
            }
            progress?.draw(canvas)
        }
    }

    fun computeTopOfIndicator(): Float =
        paddingTop + getIndicatorStrokeWidth() + getIndicatorProgressSize()

    fun computeLeftOfIndicator(): Float =
        paddingLeft + indicatorSpace + getIndicatorStrokeWidth() + getIndicatorProgressSize()

    private fun getIndicatorStrokeWidth(): Float {
        if (paint.style != Paint.Style.FILL) {
            return paint.strokeWidth
        }
        return 0.0F
    }

    private fun getIndicatorProgressSize(): Int {
        return indicatorSize
    }

    protected fun remeasuringIndicators() {
        val strokeWidth = getIndicatorStrokeWidth()
        val y: Float = computeTopOfIndicator()
        var x: Float = computeLeftOfIndicator()

        for (info in infoList) {
            info.y = y
            info.x = x
            x += indicatorSize + indicatorSpace + strokeWidth
        }

        x += getIndicatorProgressSize() / 2f
        remeasuringSize(x, y)
        invalidate()
    }

    private fun remeasuringSize(x: Float, y: Float) {
        val w = x.toInt() + paddingRight
        val h =
            (y + indicatorSize + paddingBottom + getIndicatorStrokeWidth() + getIndicatorProgressSize()).toInt()

        minimumWidth = w
        minimumHeight = h

        syncIndicatorProgress()
    }

    fun getCount(): Int = pager.count

    fun getCurrentItem(): Int {
        return pager.currentItem
    }

    fun setIndicatorStyle(style: Paint.Style) {
        paint.style = style
    }

    fun setIndicatorStrokeWidth(strokeWidth: Float) {
        paint.strokeWidth = strokeWidth
    }

    private fun syncIndicatorProgress() {
        progress?.let {
            if (it.keepDraw) {
                it.virtualScroll()
            }
        }
    }

    private fun setupIndicatorsInfo() {
        val strokeWidth = getIndicatorStrokeWidth()
        val y: Float = computeTopOfIndicator()
        var x: Float = computeLeftOfIndicator()
        val count = getCount()

        for (i in 0 until count) {
            infoList.add(IndicatorInfo(x, y))
            x += indicatorSize + indicatorSpace + strokeWidth
        }

        x += getIndicatorProgressSize() / 2f
        remeasuringSize(x, y)
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        if (!this::pager.isInitialized) {
            pager = Pager(viewPager)
            setup()
        }
    }

    fun setupWithViewPager2(viewPager2: ViewPager2) {
        if (!this::pager.isInitialized) {
            pager = Pager(viewPager2)
            setup()
        }
    }

    private fun setup() {
        pager.addOnPageChangeListener(onPageChangeListener)
        setupIndicatorsInfo()
        setupWithViewPager = true
    }
}