package com.mahdikh.vision.viewpagerindicator.widget;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

class Pager {
    private ViewPager viewPager;
    private ViewPager2 viewPager2;
    private ViewPager.OnPageChangeListener onPageChangeListener = null;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback = null;

    public Pager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public Pager(ViewPager2 viewPager2) {
        this.viewPager2 = viewPager2;
    }

    public int getCount() {
        if (viewPager != null) {
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                return adapter.getCount();
            }
        } else {
            RecyclerView.Adapter<?> adapter = viewPager2.getAdapter();
            if (adapter != null) {
                return adapter.getItemCount();
            }
        }
        return 0;
    }

    public void setCurrentItem(int position, boolean smooth) {
        if (viewPager != null) {
            viewPager.setCurrentItem(position, smooth);
        } else {
            viewPager2.setCurrentItem(position, smooth);
        }
    }

    public int getCurrentItem() {
        if (viewPager != null) {
            return viewPager.getCurrentItem();
        }
        return viewPager2.getCurrentItem();
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(onPageChangeListener);
        } else {
            viewPager2.registerOnPageChangeCallback(createOnPageChangeCallback());
        }
        this.onPageChangeListener = onPageChangeListener;
    }

    public void removeOnPageChangeListener() {
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(onPageChangeListener);
        } else {
            viewPager2.unregisterOnPageChangeCallback(onPageChangeCallback);
        }
    }

    private ViewPager2.OnPageChangeCallback createOnPageChangeCallback() {
        if (onPageChangeCallback == null) {
            onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    onPageChangeListener.onPageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            };
        }
        return onPageChangeCallback;
    }
}