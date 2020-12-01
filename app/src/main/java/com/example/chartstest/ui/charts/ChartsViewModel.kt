package com.example.chartstest.ui.charts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.chartstest.models.StockData
import com.example.chartstest.networking.Repository
import com.example.chartstest.networking.Resource
import com.example.chartstest.utils.Constants.Companion.errorMessage
import com.example.chartstest.utils.extentions.getPerformance
import com.example.chartstest.utils.extentions.setDefaultAppearance
import com.example.chartstest.utils.extentions.toCandleEntry
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.Dispatchers
import java.util.*

class ChartsViewModel(private val repository: Repository) : ViewModel() {

    val lineData: MutableLiveData<LineData> by lazy {
        MutableLiveData<LineData>()
    }

    val candleData: MutableLiveData<MutableList<CandleData>?> by lazy {
        MutableLiveData<MutableList<CandleData>?>()
    }

    // Responsible for fetching weekly data through networking
    fun getWeeklyRequest() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getWeeklyData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: errorMessage))
        }
    }

    // Responsible for fetching monthly data through networking
    fun getMonthlyRequest() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getMonthlyData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: errorMessage))
        }
    }

    /**
     * Gets data for line chart - calculates quotes performances,
     * wraps them into the data for multiple chart with basic settings
     *
     * @param data - initial data about all stocks
     */
    fun processLineChartsData(data: StockData?) {
        // needed for different lines colors
        var mapEntryIndex = 0
        val dataSets: MutableList<ILineDataSet>? = data?.content?.quoteSymbols?.map { symbol ->
            val performance = symbol.getPerformance()
            val entries =
                performance.map { Entry(it.timeStamp.toFloat(), it.performance.toFloat()) }
            val d = LineDataSet(entries, symbol.symbol)

            d.setDefaultAppearance(mapEntryIndex)

            mapEntryIndex++
            return@map d

        }?.toMutableList()

        lineData.value = LineData(dataSets)
    }


    /**
     * Gets data for line chart - just collects all needed data from quotes
     * wraps them into the data for list with candlestickCharts
     *
     * @param data - initial data about all stocks
     */
    fun processCandleStickChartsData(data: StockData?) {
        candleData.value = data?.content?.quoteSymbols?.map { symbol ->
            val values = ArrayList<CandleEntry>()
            symbol.timestamps.forEachIndexed { index, _ ->
                values.add(symbol.toCandleEntry(index))
            }
            val set = CandleDataSet(values, symbol.symbol)

            set.setDefaultAppearance()

            return@map CandleData(set)
        }?.toMutableList()
    }

}