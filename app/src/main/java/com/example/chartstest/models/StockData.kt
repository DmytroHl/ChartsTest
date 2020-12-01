package com.example.chartstest.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  StockData (val content: StockDataContent, val status: String)

