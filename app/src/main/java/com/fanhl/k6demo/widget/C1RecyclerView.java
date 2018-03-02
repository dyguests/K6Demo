package com.fanhl.k6demo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * desc:
 * date: 2018/3/2
 *
 * @author fanhl
 */
public class C1RecyclerView extends RecyclerView {
    /** TAG */
    private static final String TAG = C1RecyclerView.class.getSimpleName();
    
    public C1RecyclerView(Context context) {
        super(context);
    }

    public C1RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public C1RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
