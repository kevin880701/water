package com.lhr.water.ui.base

import android.content.Context
import android.preference.PreferenceManager
import com.lhr.water.data.Repository.FormRepository
import com.lhr.water.data.Repository.RegionRepository
import kotlinx.coroutines.*
import timber.log.Timber

@ExperimentalCoroutinesApi
class AppContainer(private val context: Context) {

    val regionRepository by lazy { RegionRepository.getInstance(context) }

    val formRepository by lazy { FormRepository.getInstance(context) }


    val viewModelFactory: AppViewModelFactory by lazy {
        AppViewModelFactory(
            context,
            regionRepository,
            formRepository
        )
    }
}
