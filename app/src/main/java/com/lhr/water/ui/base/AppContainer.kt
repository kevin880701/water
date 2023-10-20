package com.lhr.water.ui.base

import android.content.Context
import android.preference.PreferenceManager
import com.lhr.water.data.FormRepository
import com.lhr.water.data.RegionRepository
import kotlinx.coroutines.*
import timber.log.Timber

@ExperimentalCoroutinesApi
class AppContainer(private val context: Context) {


    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, e ->
        Timber.e(e, "appCoroutineScope coroutineExceptionHandler")
    }

    private val appCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineName("ApplicationScope") + coroutineExceptionHandler)

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    val regionRepository = RegionRepository()

    val formRepository = FormRepository()


    val viewModelFactory: AppViewModelFactory by lazy {
        AppViewModelFactory(
            context,
            formRepository,
            regionRepository
        )
    }
}
