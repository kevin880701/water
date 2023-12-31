package com.lhr.water.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.ui.cover.CoverViewModel
import com.lhr.water.ui.form.FormViewModel
import com.lhr.water.ui.formContent.FormContentViewModel
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.ui.main.MainViewModel
import com.lhr.water.ui.map.MapViewModel
import com.lhr.water.ui.materialSearch.MaterialSearchViewModel
import com.lhr.water.ui.qrCode.QrCodeViewModel
import com.lhr.water.ui.regionChoose.RegionChooseViewModel
import com.lhr.water.ui.setting.SettingViewModel

class AppViewModelFactory(
    private val context: Context,
    private val regionRepository: RegionRepository,
    private val formRepository: FormRepository,

    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CoverViewModel::class.java -> CoverViewModel(context, regionRepository) as T
            LoginViewModel::class.java -> LoginViewModel(context, regionRepository) as T
            MainViewModel::class.java -> MainViewModel(context, formRepository) as T
            FormViewModel::class.java -> FormViewModel(context) as T
            FormContentViewModel::class.java -> FormContentViewModel(context, formRepository) as T
            HistoryViewModel::class.java -> HistoryViewModel(context, regionRepository, formRepository) as T
            SettingViewModel::class.java -> SettingViewModel(context, formRepository) as T
            RegionChooseViewModel::class.java -> RegionChooseViewModel(context, regionRepository) as T
            MapViewModel::class.java -> MapViewModel(context, regionRepository, formRepository) as T
            QrCodeViewModel::class.java -> QrCodeViewModel(context) as T
            MaterialSearchViewModel::class.java -> MaterialSearchViewModel(context, formRepository) as T
            else -> throw IllegalArgumentException("not support")
        }
    }
}