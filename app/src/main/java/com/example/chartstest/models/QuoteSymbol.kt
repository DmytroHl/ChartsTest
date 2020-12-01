package com.example.chartstest.models
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuoteSymbol (
        val symbol: String,
        val timestamps: List<Int>,
        val opens: List<Double>,
        val closures: List<Double>,
        val highs: List<Double>,
        val lows: List<Double>,
        val volumes: List<Int>
)
