package com.fanhl.k6demo

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_main.*

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