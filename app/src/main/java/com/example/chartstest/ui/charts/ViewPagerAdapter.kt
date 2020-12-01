package com.example.chartstest.ui.charts

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chartstest.ui.charts.candlestickChart.CandleStickChartsFragment
import com.example.chartstest.ui.charts.lineChart.LineChartFragment
import com.example.chartstest.utils.Constants.Companion.filePathKey

class ViewPagerAdapter(fragment: Fragment, val pathId: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> LineChartFragment()
            else -> CandleStickChartsFragment()
        }

        fragment.arguments = Bundle().apply {
            putInt(filePathKey, pathId)
        }
        return fragment
    }
}