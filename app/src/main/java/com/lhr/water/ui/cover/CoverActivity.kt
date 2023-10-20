package com.lhr.water.ui.cover

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.lhr.water.room.SqlDatabase
import com.lhr.water.R
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.model.Model
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.login.LoginActivity
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.ui.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoverActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}

    private var _binding: ActivityCoverBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)

        // 創建Model
        Model
        // 讀取資料並轉成LIST
        var sqlManager = SqlDatabase(this)
        GlobalScope.launch {
//            allTargetEntityArrayList = sqlManager.getClassDao().getAll() as ArrayList<TargetEntity>
//            if(allTargetDataArrayList.size == 0){
//                regionNameArrayList = FakerData.targetEntities.map { it.regionName }.distinct() as ArrayList<String>
//                allTargetDataArrayList = FakerData.targetEntities
//            }else{
//                // 將 TargetEntity 轉換成 TargetData 並去掉 id 欄位
//                allTargetDataArrayList = allTargetEntityArrayList.map { targetEntity ->
//                    TargetData(
//                        targetRegion = targetEntity.targetRegion,
//                        targetRegionNum = targetEntity.targetRegionNum,
//                        targetName = targetEntity.targetName,
//                        targetNum = targetEntity.targetNum,
//                        targetCoordinateX = targetEntity.targetCoordinateX,
//                        targetCoordinateY = targetEntity.targetCoordinateY,
//                        targetType = targetEntity.targetType,
//                        targetTypeNum = targetEntity.targetTypeNum
//                    )
//                } as ArrayList<TargetData>
//                Model.regionNameArrayList = Model.allTargetEntityArrayList.map { it.targetRegion }.distinct() as ArrayList<String>
//            }

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