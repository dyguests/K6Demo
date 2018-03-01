package com.fanhl.k6demo

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

/**
 * desc:
 * date: 2018/3/1
 *
 * @author fanhl
 */
class SearchBarBehavior : CoordinatorLayout.Behavior<View> {
    var enableCallback: (() -> Boolean)? = null

    constructor() {}

    constructor(enableCallback: () -> Boolean) {
        this.enableCallback = enableCallback
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return (enableCallback?.invoke() == true) and ((nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed)
//        Log.i(TAG, "onNestedPreScroll dx:$dx dy:$dy")
        val leftScrolled = target!!.scrollY
        //        child.setScrollY(leftScrolled);
//        (child as TextView).text = "leftScrolled:" + leftScrolled
        ViewCompat.offsetTopAndBottom(child, dy)
    }

    companion object {
        /** TAG  */
        private val TAG = SearchBarBehavior::class.java.simpleName
    }

}
