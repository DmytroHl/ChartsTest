package com.example.chartstest.ui.charts.candlestickChart

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chartstest.R
import com.example.chartstest.databinding.CandlestickChartFragmentBinding
import com.example.chartstest.networking.ChartsApiFactory
import com.example.chartstest.networking.Repository
import com.example.chartstest.networking.ResponseStatus
import com.example.chartstest.ui.charts.ChartsViewModel
import com.example.chartstest.ui.charts.ViewModelFactory
import com.example.chartstest.ui.main.MainActivity
import com.example.chartstest.utils.Constants
import com.example.chartstest.utils.Constants.Companion.itemOffset
import com.example.chartstest.utils.VerticalSpaceItemDecoration
import com.example.chartstest.utils.extentions.dpToPx
import com.example.chartstest.utils.extentions.hideView
import com.example.chartstest.utils.extentions.showView
import com.example.chartstest.utils.extentions.viewBinding
import com.github.mikephil.charting.data.CandleData

class CandleStickChartsFragment : Fragment(R.layout.candlestick_chart_fragment) {

    private val binding by viewBinding(CandlestickChartFragmentBinding::bind)
    private lateinit var adapter: CandleChartsAdapter

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
                        viewModel.processCandleStickChartsData(it.data)
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
                        viewModel.processCandleStickChartsData(it.data)
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

    private fun setupRecyclerView(data: MutableList<CandleData>) {
        adapter = CandleChartsAdapter().apply {
            setItems(data)
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.rvCharts.layoutManager = layoutManager
        binding.rvCharts.adapter = adapter
        binding.rvCharts.addItemDecoration(VerticalSpaceItemDecoration(itemOffset.dpToPx()))
    }


    private fun setupObservers() {
        viewModel.candleData.observe(viewLifecycleOwner, Observer { candlesChartsData ->
            candlesChartsData?.let {
                setupRecyclerView(candlesChartsData)
            }
        })
    }

}