package com.example.chartstest

import android.app.Application
import com.example.chartstest.utils.extentions.readJsonAsset

class ChartApplication : Application() {

    companion object {
        var weeklyDataJson: String? = null
        var monthlyDataJson: String? = null
    }

    override fun onCreate() {
        super.onCreate()
        weeklyDataJson =
            this.readJsonAsset(resources.getString(R.string.weekly_path))
        monthlyDataJson =
            this.readJsonAsset(resources.getString(R.string.monthly_path))
    }
}