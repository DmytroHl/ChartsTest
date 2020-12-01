package com.example.chartstest.utils.extentions

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.chartstest.models.Performance
import com.example.chartstest.models.QuoteSymbol
import com.example.chartstest.models.StockData
import com.example.chartstest.utils.Constants
import com.example.chartstest.utils.FragmentViewBindingInterface
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.LineDataSet
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.IOException
import java.io.InputStream
import kotlin.jvm.Throws

@Throws(IOException::class)

        /**
         * Responsible for getting data from files in assets folder
         */
fun Context.readJsonAsset(fileName: String) : String {
    val inputStream = assets.open(fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charsets.UTF_8)
}

fun QuoteSymbol.getPerformance(): MutableList<Performance> {
    if (!opens.isNullOrEmpty()) {
        val price = opens.first()
        return timestamps.zip(opens).map { data ->
            val value = 100 * data.second / price - 100
            return@map Performance(data.first, value)
        }.toMutableList()
    } else {
        return mutableListOf()
    }
}

fun QuoteSymbol.toCandleEntry(index: Int): CandleEntry {
    val highs = highs[index]
    val lows = lows[index]
    val opens = opens[index]
    val closes = closures[index]

    return CandleEntry(
        index.toFloat(),
        highs.toFloat(),
        lows.toFloat(),
        opens.toFloat(),
        closes.toFloat()
    )
}


fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun View.showView(){
    visibility = VISIBLE
}

fun View.hideView(){
    visibility = GONE
}

/**
 *  A lazy delegates that inflate the binding for fragment
 */
fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingInterface(this, viewBindingFactory)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }


/**
 * Appearance setup for charts
 */
fun CandleDataSet.setDefaultAppearance() {
    setDrawIcons(false)
    axisDependency = YAxis.AxisDependency.LEFT
    shadowColor = Color.DKGRAY
    shadowWidth = 0.7f
    decreasingColor = Color.RED
    decreasingPaintStyle = Paint.Style.FILL
    increasingColor = Color.rgb(122, 242, 84)
    increasingPaintStyle = Paint.Style.STROKE
    neutralColor = Color.BLUE
}

fun LineDataSet.setDefaultAppearance(index: Int){
    lineWidth = 2.5f
    circleRadius = 4f
    circleRadius = 2f

    // provides different line colors
    val lineColor: Int = Constants.colors[index % Constants.colors.size]
    color = lineColor
    setCircleColor(lineColor)
}
fun LineChart.setDefaultAppearance() {
    description.isEnabled = false
    setDrawBorders(false)
    axisLeft.isEnabled = false
    axisRight.setDrawAxisLine(false)
    xAxis.setDrawAxisLine(false)

    setTouchEnabled(true)
    setPinchZoom(false)

    isDragEnabled = false
    setScaleEnabled(true)


    val l: Legend = legend
    l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
    l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
    l.orientation = Legend.LegendOrientation.VERTICAL
    l.setDrawInside(false)
}

fun CandleStickChart.setDefaultAppearance() {
    description.isEnabled = false

    setMaxVisibleValueCount(500)

    isDragEnabled = true
    setScaleEnabled(true)
    setPinchZoom(true)

    val xAxis: XAxis = xAxis
    xAxis.position = XAxisPosition.BOTTOM
    xAxis.setDrawGridLines(false)

    val leftAxis: YAxis = axisLeft
    leftAxis.setLabelCount(7, false)
    leftAxis.setDrawGridLines(false)
    leftAxis.setDrawAxisLine(false)

    val rightAxis: YAxis = axisRight
    rightAxis.isEnabled = false


    val l: Legend = legend
    l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
    l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
    l.orientation = Legend.LegendOrientation.VERTICAL
    l.setDrawInside(false)
}

