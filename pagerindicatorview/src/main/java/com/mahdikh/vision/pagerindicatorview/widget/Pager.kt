package com.mahdikh.vision.pagerindicatorview.widget

import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2

class Pager {
    private val mImpl: Impl

    constructor(viewPager: ViewPager) {
        mImpl = ViewPagerImpl(viewPager)
    }

    constructor(viewPager2: ViewPager2) {
        mImpl = ViewPager2Impl(viewPager2)
    }

    fun getCount(): Int {
        return mImpl.getCount()
    }

    fun getCurrentItem(): Int {
        return mImpl.getCurrentItem()
    }

    fun addOnPageChangeListener(listener: OnPageChangeListener) {
        mImpl.addOnPageChangeListener(listener)
    }

    fun removeOnPageChangeListener(listener: OnPageChangeListener) {
        mImpl.removeOnPageChangeListener(listener)
    }

    private abstract class Impl {
        abstract fun getCount(): Int

        abstract fun getCurrentItem(): Int

        abstract fun addOnPageChangeListener(listener: OnPageChangeListener)

        abstract fun removeOnPageChangeListener(listener: OnPageChangeListener)
    }

    private class ViewPagerImpl(private val viewPager: ViewPager) : Impl() {
        override fun getCount(): Int {
            return viewPager.adapter?.count ?: 0
        }

        override fun getCurrentItem(): Int {
            return viewPager.currentItem
        }

        override fun addOnPageChangeListener(listener: OnPageChangeListener) {
            viewPager.addOnPageChangeListener(listener)
        }

        override fun removeOnPageChangeListener(listener: OnPageChangeListener) {
            viewPager.removeOnPageChangeListener(listener)
        }
    }

    private class ViewPager2Impl(private val viewPager2: ViewPager2) : Impl() {
        private val callbacks: MutableList<ViewPager2.OnPageChangeCallback> = mutableListOf()
        private val listeners: MutableList<OnPageChangeListener> = mutableListOf()

        override fun getCount(): Int {
            return viewPager2.adapter?.itemCount ?: 0
        }

        override fun getCurrentItem(): Int {
            return viewPager2.currentItem
        }

        override fun addOnPageChangeListener(listener: OnPageChangeListener) {
            val callback = object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    listener.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    listener.onPageScrolled(
                        position,
                        positionOffset,
                        positionOffsetPixels
                    )
                }

                override fun onPageSelected(position: Int) {
                    listener.onPageSelected(position)
                }
            }
            viewPager2.registerOnPageChangeCallback(callback)
            listeners.add(listener)
            callbacks.add(callback)
        }

        override fun removeOnPageChangeListener(listener: OnPageChangeListener) {
            val index = listeners.indexOf(listener)
            viewPager2.unregisterOnPageChangeCallback(callbacks[index])
            listeners.removeAt(index)
            callbacks.removeAt(index)
        }
    }
}