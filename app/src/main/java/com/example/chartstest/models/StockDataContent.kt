package com.example.chartstest.models
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  StockDataContent (val quoteSymbols: List<QuoteSymbol>)