package com.example.chartstest.utils

import android.graphics.Color

class Constants {
    companion object {
        val filePathKey: String = "file_path"
        val itemOffset = 25
        val BASE_URL = "https://www.google.com/"
        val weekEndpoint = "quotes_week"
        val monthEndpoint = "quotes_month"
        val errorMessage = "Error occurred!"
        val viewBindingError = "Should not attempt to get bindings when Fragment views are destroyed."
        val colors = intArrayOf(
            Color.rgb(64, 89, 128),
            Color.rgb(149, 165, 124),
            Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134),
            Color.rgb(179, 48, 80)
        )
    }
}