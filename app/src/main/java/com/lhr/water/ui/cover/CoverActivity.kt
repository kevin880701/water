package com.lhr.water.ui.cover

import android.content.Intent
import android.icu.util.TaiwanCalendar
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.R
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.model.Model
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.login.LoginActivity
import com.lhr.water.util.getCurrentDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CoverActivity : BaseActivity() {

    private val viewModel: CoverViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}

    private var _binding: ActivityCoverBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)
        // 創建Model
        Model

        viewModel.checkTable()
        Timber.d("---------------------------------------")
        Timber.d("SIZE：${viewModel.regionRepository.regionEntities.value?.size}")
        Timber.d("SIZE：${viewModel.regionRepository.mapEntities.value?.size}")
        Timber.d("SIZE：${viewModel.regionRepository.mapEntities.value?.size}")
        Timber.d("---------------------------------------")

        GlobalScope.launch {
            val layout = findViewById<ConstraintLayout>(R.id.constrain)
            val animation = AlphaAnimation(0.0f, 1.0f)
            animation.fillAfter = true
            animation.duration = 1000
            layout.startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    println("動畫開始")
                }

                override fun onAnimationEnd(animation: Animation) {
                    println("動畫結束")

                    val intent = Intent(this@CoverActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onAnimationRepeat(animation: Animation) {
                    println("動畫重覆執行")
                }
            })
        }
    }
}