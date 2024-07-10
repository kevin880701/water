package com.lhr.water.ui.cover

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.lhr.water.network.ApiManager
import com.lhr.water.network.data.response.UserInfo
import com.lhr.water.repository.FormRepository
import com.lhr.water.repository.RegionRepository
import com.lhr.water.repository.UserRepository
import com.lhr.water.ui.base.APP
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class CoverViewModel(
    var context: Context,
    var formRepository: FormRepository,
    var regionRepository: RegionRepository,
    var userRepository: UserRepository
) : AndroidViewModel(context.applicationContext as APP) {

}