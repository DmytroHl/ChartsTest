package com.example.chartstest.ui.charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chartstest.networking.Repository

class ViewModelFactory(private val apiRepository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChartsViewModel::class.java)) {
            return ChartsViewModel(apiRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}