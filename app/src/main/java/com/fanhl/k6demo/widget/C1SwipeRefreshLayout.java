package com.fanhl.k6demo.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * desc: 这里重写下事件分发
 *
 * CoordinatorLayout
 * AppBarLayout
 * ViewPager
 * Fragment
 * SwipeRefreshLayout
 * RecyclerView
 *
 * 下拉的顺序要修改成 RecyclerView>AppBarLayout>SwipeRefreshLayout
 * 直接用SwipeRefreshLayout的话 AppBarLayout SwipeRefreshLayout会冲突
 *
 * date: 2018/3/1
 *
 * @author fanhl
 */
public class C1SwipeRefreshLayout extends SwipeRefreshLayout {
    /** TAG */
    private static final String TAG = C1SwipeRefreshLayout.class.getSimpleName();

    public C1SwipeRefreshLayout(Context context) {
        super(context);
    }

    public C1SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "事件分发 onInterceptTouchEvent ev:" + ev.getAction() + " b:" + b);
        return b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        Log.d(TAG, "事件分发 dispatchTouchEvent ev:" + ev.getAction() + " b:" + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b = super.onTouchEvent(ev);
        Log.d(TAG, "事件分发 onTouchEvent ev:" + ev.getAction() + " b:" + b);
        return b;
    }
}
