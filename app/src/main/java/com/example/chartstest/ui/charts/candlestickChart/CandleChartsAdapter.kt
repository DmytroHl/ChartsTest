package com.example.chartstest.ui.charts.candlestickChart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chartstest.databinding.ItemCandlestickChartBinding
import com.example.chartstest.utils.extentions.setDefaultAppearance
import com.github.mikephil.charting.data.CandleData

class CandleChartsAdapter : RecyclerView.Adapter<CandleChartsAdapter.ViewHolder>() {

    private var items: MutableList<CandleData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCandlestickChartBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: MutableList<CandleData>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCandlestickChartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: CandleData) {
            binding.chart.setDefaultAppearance()
            binding.chart.data = subject
        }
    }
}