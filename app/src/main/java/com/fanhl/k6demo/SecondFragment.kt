package com.fanhl.k6demo

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * A placeholder fragment containing a simple view.
 */
class SecondFragment : Fragment() {
    val sectionNumber by lazy { arguments.getInt(ARG_SECTION_NUMBER) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
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
//        (tv_search.layoutParams as CoordinatorLayout.LayoutParams).behavior = SearchBarBehavior {
//            getSearchBehaviorEnable()
//        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var defaultTvSearchY: Float? = null
            var totalOffset = 0F
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val offsetMaxBound = (tv_search.layoutParams as ViewGroup.MarginLayoutParams).topMargin + tv_search.height

                if (defaultTvSearchY == null) {
                    defaultTvSearchY = (tv_search.layoutParams as ViewGroup.MarginLayoutParams).topMargin.toFloat()
                    Log.d(TAG, "onScrolled defaultTvSearchY:$defaultTvSearchY")
                }
                totalOffset = defaultTvSearchY!! - tv_search.y

                Log.d(TAG, "onScrolled dx:$dx dy:$dy")
                if (totalOffset < offsetMaxBound && dy > 0) {
                    //优化后的dy，防止越界
                    val oDy = if (totalOffset + dy <= offsetMaxBound) {
                        dy.toFloat()
                    } else {
                        offsetMaxBound - totalOffset
                    }

//                    totalOffset += oDy
                    ViewCompat.offsetTopAndBottom(tv_search, -oDy.toInt())
//                    totalOffset = defaultTvSearchY!! - tv_search.y

                } else if (totalOffset > 0 && dy < 0) {
                    //优化后的dy，防止越界
                    val oDy = if (totalOffset + dy >= 0) {
                        dy.toFloat()
                    } else {
                        -totalOffset
                    }

//                    totalOffset += oDy
                    ViewCompat.offsetTopAndBottom(tv_search, -oDy.toInt())
//                    totalOffset = defaultTvSearchY!! - tv_search.y
                }

                Log.d(TAG, "onScrolled defaultTvSearchY:$defaultTvSearchY totalOffset:$totalOffset offsetMaxBound:$offsetMaxBound")
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d(TAG, "onScrollStateChanged idle totalOffset:$totalOffset")
                    if (totalOffset > 0) {
                        tv_search.animate().translationYBy(totalOffset)
                    }
                }
            }
        })
    }

    private fun getSearchBehaviorEnable() =
            !(((activity as MainActivity).appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior as K6AppBarLayoutBehavior).enabled

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
        /** TAG */
        private val TAG = SecondFragment::class.java.simpleName!!
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(sectionNumber: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}