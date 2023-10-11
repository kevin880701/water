package com.lhr.water.ui.cover

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lhr.water.room.SqlDatabase
import com.lhr.water.room.TargetEntity
import com.lhr.water.R
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.model.Model
import com.lhr.water.model.Model.Companion.allTargetDataArrayList
import com.lhr.water.model.Model.Companion.allTargetEntityArrayList
import com.lhr.water.model.Model.Companion.regionNameArrayList
import com.lhr.water.model.RoomData
import com.lhr.water.model.TargetData
import com.lhr.water.ui.main.MainActivity
import com.lhr.water.ui.map.MapActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoverActivity : AppCompatActivity() {

    lateinit var viewModel: CoverViewModel
    lateinit var binding: ActivityCoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cover)
        viewModel = ViewModelProvider(
            this,
            CoverViewModelFactory(this.application)
        )[CoverViewModel::class.java]
        binding.viewModel = viewModel

        // 創建Model
        Model
        // 讀取資料並轉成LIST
        var sqlManager = SqlDatabase(this)
        println("PPPPPPPPPPPPPP")
        GlobalScope.launch {
            allTargetEntityArrayList = sqlManager.getClassDao().getAll() as ArrayList<TargetEntity>
            if(allTargetDataArrayList.size == 0){
                println("ASASASASASASASA")
                regionNameArrayList = RoomData.targetEntities.map { it.targetRegion }.distinct() as ArrayList<String>
                allTargetDataArrayList = RoomData.targetEntities
            }else{
                println("IOIOIOIOIOIOIOIOIOI")
                // 將 TargetEntity 轉換成 TargetData 並去掉 id 欄位
                allTargetDataArrayList = allTargetEntityArrayList.map { targetEntity ->
                    TargetData(
                        targetRegion = targetEntity.targetRegion,
                        targetRegionNum = targetEntity.targetRegionNum,
                        targetName = targetEntity.targetName,
                        targetNum = targetEntity.targetNum,
                        targetCoordinateX = targetEntity.targetCoordinateX,
                        targetCoordinateY = targetEntity.targetCoordinateY,
                        targetType = targetEntity.targetType,
                        targetTypeNum = targetEntity.targetTypeNum
                    )
                } as ArrayList<TargetData>
                Model.regionNameArrayList = Model.allTargetEntityArrayList.map { it.targetRegion }.distinct() as ArrayList<String>
            }

            // 根據Region創建Fragment LIST
//            Model.regionNameArrayList.forEachIndexed { index, itemName ->
//                Model.mapFragmentArrayList.add(MapFragment(itemName))
//            }

            val layout = findViewById<ConstraintLayout>(R.id.constrain)
            val animation = AlphaAnimation(0.0f, 1.0f)
            animation.fillAfter = true
//            animation.duration = 3500
            animation.duration = 500
            layout.startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    println("動畫開始")
                }

                override fun onAnimationEnd(animation: Animation) {
                    println("動畫結束")

                    val intent = Intent(this@CoverActivity, MainActivity::class.java)
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