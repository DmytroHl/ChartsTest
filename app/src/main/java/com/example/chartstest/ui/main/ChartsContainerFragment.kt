package com.example.chartstest.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chartstest.R
import com.example.chartstest.databinding.FragmentChartsContainerBinding
import com.example.chartstest.ui.charts.ViewPagerAdapter
import com.example.chartstest.utils.Constants
import com.example.chartstest.utils.extentions.viewBinding
import com.google.android.material.tabs.TabLayoutMediator


class ChartsContainerFragment : Fragment(R.layout.fragment_charts_container) {
    private val binding by viewBinding(FragmentChartsContainerBinding::bind)
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        setupPager()


        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
            return@setOnClickListener
        }
    }

    private fun setupPager() {
        val pathId: Int = arguments?.getInt(Constants.filePathKey) ?: R.string.weekly_path
        viewPagerAdapter = ViewPagerAdapter(this, pathId)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.linear_chart)
                1 -> tab.text = resources.getString(R.string.candlestick_chart)
            }
        }.attach()
    }
}