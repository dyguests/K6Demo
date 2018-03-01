package com.fanhl.k6demo

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /** TAG */
    private val TAG = MainActivity::class.java.simpleName!!

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        //顶部滑动交互
        appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when (state) {
                    State.COLLAPSED -> ((appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior as DisableableAppBarLayoutBehavior).apply {
                        enabled = false
                        setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                                return false
                            }
                        })
                    }
                    else -> ((appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior as DisableableAppBarLayoutBehavior).apply {
                        enabled = true
                        setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                                return true
                            }
                        })
                    }
                }
            }
        })
        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val collapsedPercent = -verticalOffset / appBarLayout.totalScrollRange.toFloat()

            //仅在这 0.3的区间里交互 //这个值 在  （0,1]之间，可以自定义
            //这里表示仅在收缩到仅剩百分比hotPercent的时候，才进行收缩
            val hotPercent = 0.3F

            val collapsedHotPercent = Math.max(-1F, collapsedPercent / hotPercent - 1 / hotPercent)

            Log.d(TAG, "onCreate collapsedPercent:" + collapsedPercent)

            ll_tabs.apply {
                ll_tabs_left.apply widthProvider@ {
                    layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                        leftMargin = (this@widthProvider.width * collapsedHotPercent).toInt()
                    }
                }
                ll_tabs_right.apply widthProvider@ {
                    layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                        rightMargin = (this@widthProvider.width * collapsedHotPercent).toInt()
                    }
                }
            }
        }

        tv_left.setOnClickListener { appbar.setExpanded(true, true) }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FirstFragment.newInstance(position + 1)
                else -> SecondFragment.newInstance(position + 1)
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }
    }

}
