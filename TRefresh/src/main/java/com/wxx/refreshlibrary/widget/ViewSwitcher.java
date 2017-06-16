package com.wxx.refreshlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class ViewSwitcher extends ViewGroup {

    public ViewSwitcher(Context context) {
        this(context, null);
    }

    public ViewSwitcher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewSwitcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = this.getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            this.measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            maxWidth = childView.getMeasuredWidth();
            maxHeight = childView.getMeasuredHeight();
        }

        setMeasuredDimension(maxWidth, maxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                childView.layout(0, 0, r - l, b - t);
            }

        }
    }

    public void setView(View view) {
        if (this.getChildCount() != 0) {
            this.removeViewAt(0);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(64, 64);
        this.addView(view);
    }
}
