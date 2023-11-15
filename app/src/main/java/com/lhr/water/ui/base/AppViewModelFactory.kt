package com.lhr.water.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lhr.water.ui.cover.CoverViewModel
import com.lhr.water.ui.form.FormViewModel
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.ui.main.MainViewModel
import com.lhr.water.ui.map.MapViewModel
import com.lhr.water.ui.mapChoose.MapChooseViewModel
import com.lhr.water.ui.qrCode.QrCodeViewModel
import com.lhr.water.ui.regionChoose.RegionChooseViewModel

class AppViewModelFactory(
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CoverViewModel::class.java -> CoverViewModel(context) as T
            LoginViewModel::class.java -> LoginViewModel(context) as T
            MainViewModel::class.java -> MainViewModel(context) as T
            FormViewModel::class.java -> FormViewModel(context) as T
            HistoryViewModel::class.java -> HistoryViewModel(context) as T
            RegionChooseViewModel::class.java -> RegionChooseViewModel(context) as T
            MapChooseViewModel::class.java -> MapChooseViewModel(context) as T
            MapViewModel::class.java -> MapViewModel(context) as T
            QrCodeViewModel::class.java -> QrCodeViewModel(context) as T
            else -> throw IllegalArgumentException("not support")
        }
    }
}