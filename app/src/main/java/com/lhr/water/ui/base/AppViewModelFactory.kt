package com.lhr.water.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.lhr.water.data.FormRepository
import com.lhr.water.data.MapRepository
import com.lhr.water.ui.cover.CoverViewModel
import com.lhr.water.ui.form.FormViewModel
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.ui.main.MainViewModel
import com.lhr.water.ui.map.MapViewModel
import com.lhr.water.ui.mapChoose.MapChooseViewModel

class AppViewModelFactory(
    private val context: Context,
    private val formRepository: FormRepository,
    private val mapRepository: MapRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){
            CoverViewModel::class.java -> CoverViewModel(context) as T
            LoginViewModel::class.java -> LoginViewModel(context) as T
            MainViewModel::class.java -> MainViewModel(context) as T
            FormViewModel::class.java -> FormViewModel(context, formRepository) as T
            MapChooseViewModel::class.java -> MapChooseViewModel(context, mapRepository) as T
            MapViewModel::class.java -> MapViewModel(context, mapRepository) as T
            else -> throw IllegalArgumentException("not support")
        }
    }
}