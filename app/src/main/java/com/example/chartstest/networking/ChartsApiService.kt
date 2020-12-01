package com.example.chartstest.networking

import com.example.chartstest.models.StockData
import retrofit2.http.GET

interface ChartsApiService {
    @GET("quotes_week")
    suspend fun getQuotesWeek(): StockData

    @GET("quotes_month")
    suspend fun getQuotesMonth(): StockData
}