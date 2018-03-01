package com.fanhl.k6demo

import android.os.AsyncTask
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
import android.view.*
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : AppCompatActivity() {
    /** TAG */
    private val TAG = MainActivity::class.java.simpleName!!

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
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
            val collapsedParent = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            Log.d(TAG, "onCreate collapsedParent:" + collapsedParent)

            ll_tabs.apply {
                ll_tabs_left.apply widthProvider@ {
                    layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                        leftMargin = (this@widthProvider.width * (collapsedParent - 1)).toInt()
                    }
                }
                ll_tabs_right.apply widthProvider@ {
                    layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                        rightMargin = (this@widthProvider.width * (collapsedParent - 1)).toInt()
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {
        val sectionNumber by lazy { arguments.getInt(ARG_SECTION_NUMBER) }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = if (sectionNumber == 1) {
                inflater.inflate(R.layout.fragment_main, container, false)
            } else {
                inflater.inflate(R.layout.fragment_search, container, false)
            }
            return rootView
        }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            swipe_refresh_layout.apply {
                setOnRefreshListener { refreshData() }
            }
            recycler_view.apply {
                adapter = object : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_view, listOf(1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3)) {
                    override fun convert(helper: BaseViewHolder?, item: Int?) {
                    }
                }
            }
        }

        private fun refreshData() {
            object : AsyncTask<Void, Void, Void?>() {
                override fun doInBackground(vararg params: Void?): Void? {
                    Thread.sleep(2000)
                    return null
                }

                override fun onPostExecute(result: Void?) {
                    swipe_refresh_layout.isRefreshing = false
                }
            }.execute()
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
