package com.lhr.water.ui.base

import android.content.Context
import android.preference.PreferenceManager
import kotlinx.coroutines.*
import timber.log.Timber

@ExperimentalCoroutinesApi
class AppContainer(private val context: Context) {


    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, e ->
        Timber.e(e, "appCoroutineScope coroutineExceptionHandler")
    }

    private val appCoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineName("ApplicationScope") + coroutineExceptionHandler)

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

//    var regionRepository = RegionRepository.getInstance(SqlDatabase.getInstance().getTargetDao())
//
//    val formRepository = FormRepository()


    val viewModelFactory: AppViewModelFactory by lazy {
        AppViewModelFactory(
            context
        )
    }
}
