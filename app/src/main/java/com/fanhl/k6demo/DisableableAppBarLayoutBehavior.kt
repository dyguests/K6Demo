package com.fanhl.k6demo

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

/**
 * desc:
 * date: 2018/2/28
 *
 * @author fanhl
 */
class DisableableAppBarLayoutBehavior : AppBarLayout.Behavior {
    var enabled = true // enabled by default

    constructor() : super() {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onStartNestedScroll(parent: CoordinatorLayout?, child: AppBarLayout?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return enabled && super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: AppBarLayout?, target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        if (!enabled) return
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }
}