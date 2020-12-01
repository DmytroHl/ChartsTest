package com.example.chartstest.networking

class Repository(private val apiService: ChartsApiService) {

    suspend fun getWeeklyData() = apiService.getQuotesWeek()

    suspend fun getMonthlyData() = apiService.getQuotesMonth()
}