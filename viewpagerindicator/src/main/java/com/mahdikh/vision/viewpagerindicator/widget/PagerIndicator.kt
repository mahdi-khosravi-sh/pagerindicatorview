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
import com.mahdikh.vision.viewpagerindicator.indicator.abstractions.Indicator
import com.mahdikh.vision.viewpagerindicator.info.IndicatorInfo
import com.mahdikh.vision.viewpagerindicator.progress.IndicatorProgress

class PagerIndicator : View {
    private val infoList: MutableList<IndicatorInfo> = mutableListOf()
    private lateinit var pager: Pager
    var setupWithViewPager = false
        private set
    var progress: IndicatorProgress? = null
        set(value) {
            field = value
            field?.let {
                it.setPagerIndicator(this)
                it.setIndicator(indicator)
                if (setupWithViewPager) {
                    it.onReady()
                }
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
            if (setupWithViewPager) {
                indicator.onReady()
            }
        }

    init {
        indicator.pagerIndicator = this
    }

    private val onPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            private var state: Int = ViewPager.SCROLL_STATE_IDLE

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                progress?.onPageScrolled(position, positionOffset, state)
            }

            override fun onPageSelected(position: Int) {
                invalidate()
            }

            override fun onPageScrollStateChanged(state: Int) {
                this.state = state
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
                        this.indicatorSize = a.getDimensionPixelSize(index, 25)
                    }
                    R.styleable.PagerIndicator_indicatorStrokeWidth -> {
                        indicator.setStrokeWidth(a.getDimension(index, 0.0F))
                    }
                    R.styleable.PagerIndicator_indicatorStyle -> {
                        indicator.setStyle(a.getInt(index, 0))
                    }
                }
            }
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(0, 0)
    }

    internal fun getIndicatorInfo(position: Int): IndicatorInfo = infoList[position]

    internal fun getInfoList(): MutableList<IndicatorInfo> = infoList

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (setupWithViewPager) {
            progress?.onPreDraw(canvas)
            val size = infoList.size
            val current = getCurrentItem()
            for (i in 0 until size) {
                if (i == current && progress?.let { !it.inProgress } != false) {
                    indicator.setColor(selectedColor)
                } else {
                    indicator.setColor(color)
                }
                if (infoList[i].draw) {
                    indicator.onDraw(canvas, infoList[i])
                }
            }
            progress?.draw(canvas)
        }
    }

    fun getColor(position: Int): Int {
        if (getCurrentItem() == position) {
            return selectedColor
        }
        return color
    }

    fun computeTopOfIndicator(): Float = paddingTop + getIndicatorStrokeWidth() + getProgressSize()

    fun computeLeftOfIndicator(): Float = paddingLeft + indicatorSpace +
            getIndicatorStrokeWidth() + getProgressSize()

    private fun getIndicatorStrokeWidth(): Float {
        return indicator.getStrokeWidth()
    }

    private fun getProgressSize(): Int {
        return 0
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
        x += getProgressSize() / 2f
        remeasuringSize(x, y)
        indicator.onStructureChanged()
        invalidate()
    }

    private fun remeasuringSize(x: Float, y: Float) {
        val w = x.toInt() + paddingRight
        val h =
            (y + indicatorSize + paddingBottom + getIndicatorStrokeWidth() + getProgressSize()).toInt()

        minimumWidth = w
        minimumHeight = h

        if (setupWithViewPager) {
            syncIndicatorProgress()
        }
    }

    fun getCount(): Int = pager.count

    fun getCurrentItem(): Int = pager.currentItem

    fun setIndicatorStyle(style: Paint.Style) {
        indicator.setStyle(style)
    }

    fun setStrokeWidth(strokeWidth: Float) {
        indicator.setStrokeWidth(strokeWidth)
        if (setupWithViewPager) {
            remeasuringIndicators()
        }
    }

    private fun syncIndicatorProgress() {
        progress?.let {
            if (it.keepDraw) {
                it.onPageScrolled(
                    getCurrentItem(), 1.0f,
                    ViewPager.SCROLL_STATE_DRAGGING
                )
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

        x += getProgressSize() / 2f
        remeasuringSize(x, y)
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        pager = Pager(viewPager)
        setup()
    }

    fun setupWithViewPager2(viewPager2: ViewPager2) {
        pager = Pager(viewPager2)
        setup()
    }

    private fun setup() {
        pager.addOnPageChangeListener(onPageChangeListener)
        setupIndicatorsInfo()
        indicator.onReady()
        progress?.onReady()
        setupWithViewPager = true
    }
}