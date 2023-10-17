package com.lhr.water.ui.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lhr.water.R
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.databinding.ActivityMainBinding
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.form.FormFragment
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.ui.mapChoose.MapChooseFragment
import com.lhr.water.util.viewPager.MainViewPageAdapter

class MainActivity : AppCompatActivity() {

//    lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by viewModels{(applicationContext as APP).appContainer.viewModelFactory}

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var pageAdapter: MainViewPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)
        initTabLayout(binding.tabLayoutMain)
    }


    fun initTabLayout(tabLayoutMain: TabLayout) {
        tabLayoutMain.apply {
            var tabIconList = arrayListOf(
                R.drawable.form,
                R.drawable.map,
            )
            var fragments = arrayListOf(
                FormFragment(),
                MapChooseFragment(),
            ) as ArrayList<Fragment>
            pageAdapter = MainViewPageAdapter(supportFragmentManager, lifecycle, fragments)
            binding.viewPager.adapter = pageAdapter
            TabLayoutMediator(this, binding.viewPager) { tab, position ->
                tab.icon = ContextCompat.getDrawable(this.context, tabIconList[position])
            }.attach()
            binding.viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        }
    }

}