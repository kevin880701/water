package com.lhr.water.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lhr.water.data.FormRepository
import com.lhr.water.data.RegionRepository
import com.lhr.water.ui.cover.CoverViewModel
import com.lhr.water.ui.form.FormViewModel
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.ui.main.MainViewModel
import com.lhr.water.ui.map.MapViewModel
import com.lhr.water.ui.mapChoose.MapChooseViewModel
import com.lhr.water.ui.qrCode.QrCodeViewModel
import com.lhr.water.ui.regionChoose.RegionChooseViewModel

class AppViewModelFactory(
    private val context: Context,
    private val formRepository: FormRepository,
    private val regionRepository: RegionRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CoverViewModel::class.java -> CoverViewModel(context) as T
            LoginViewModel::class.java -> LoginViewModel(context) as T
            MainViewModel::class.java -> MainViewModel(context) as T
            FormViewModel::class.java -> FormViewModel(context, formRepository) as T
            RegionChooseViewModel::class.java -> RegionChooseViewModel(context, regionRepository) as T
            MapChooseViewModel::class.java -> MapChooseViewModel(context, regionRepository) as T
            MapViewModel::class.java -> MapViewModel(context, regionRepository) as T
            QrCodeViewModel::class.java -> QrCodeViewModel(context) as T
            else -> throw IllegalArgumentException("not support")
        }
    }
}