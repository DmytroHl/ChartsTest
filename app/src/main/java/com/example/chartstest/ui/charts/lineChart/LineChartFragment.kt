package com.example.chartstest.ui.charts.lineChart

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chartstest.R
import com.example.chartstest.databinding.LineChartsFragmentBinding
import com.example.chartstest.networking.ChartsApiFactory
import com.example.chartstest.networking.Repository
import com.example.chartstest.networking.ResponseStatus
import com.example.chartstest.ui.charts.ChartsViewModel
import com.example.chartstest.ui.charts.ViewModelFactory
import com.example.chartstest.utils.Constants
import com.example.chartstest.utils.Constants.Companion.filePathKey
import com.example.chartstest.utils.extentions.hideView
import com.example.chartstest.utils.extentions.setDefaultAppearance
import com.example.chartstest.utils.extentions.showView
import com.example.chartstest.utils.extentions.viewBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData


class LineChartFragment : Fragment(R.layout.line_charts_fragment) {

    private val binding by viewBinding(LineChartsFragmentBinding::bind)

    private val viewModel: ChartsViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(Repository(ChartsApiFactory.apiService))).get(
            ChartsViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        Handler(Looper.getMainLooper()).postDelayed({
            when (arguments?.getInt(Constants.filePathKey) ?: R.string.weekly_path) {
                R.string.weekly_path -> getWeeklyChartsData()
                R.string.monthly_path -> getMonthlyChartsData()
            }
        }, 1000)

    }

    private fun getWeeklyChartsData() {
        viewModel.getWeeklyRequest().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    ResponseStatus.SUCCESS -> {
                        binding.progress.hideView()
                        viewModel.processLineChartsData(it.data)
                    }
                    ResponseStatus.ERROR -> {
                        binding.progress.hideView()
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    ResponseStatus.LOADING -> {
                        binding.progress.showView()
                    }
                }
            }
        })
    }

    private fun getMonthlyChartsData() {
        viewModel.getMonthlyRequest().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    ResponseStatus.SUCCESS -> {
                        binding.progress.hideView()
                        viewModel.processLineChartsData(it.data)
                    }
                    ResponseStatus.ERROR -> {
                        binding.progress.hideView()
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    ResponseStatus.LOADING -> {
                        binding.progress.showView()
                    }
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.lineData.observe(viewLifecycleOwner, Observer { data ->
            setupLineChartView(data)
        })
    }

    private fun setupLineChartView(data: LineData) {

        binding.lineChart.xAxis.labelRotationAngle = 0f

        binding.lineChart.data = data

        binding.lineChart.setDefaultAppearance()

        binding.lineChart.animateX(1800, Easing.EaseInExpo)
    }


}