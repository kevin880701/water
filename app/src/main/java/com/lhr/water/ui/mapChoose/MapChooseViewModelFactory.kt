package com.lhr.water.ui.mapChoose

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MapChooseViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapChooseViewModel::class.java)) {
            return MapChooseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}