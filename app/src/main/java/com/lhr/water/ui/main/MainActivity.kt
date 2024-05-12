package com.lhr.water.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lhr.water.R
import com.lhr.water.databinding.ActivityMainBinding
import com.lhr.water.repository.UserRepository
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.form.FormFragment
import com.lhr.water.ui.inventory.InventoryFragment
import com.lhr.water.ui.materialSearch.MaterialSearchFragment
import com.lhr.water.ui.regionChoose.RegionChooseFragment
import com.lhr.water.ui.setting.SettingFragment
import com.lhr.water.util.viewPager.MainViewPageAdapter

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels { (applicationContext as APP).appContainer.viewModelFactory }
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageAdapter: MainViewPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryBlue, null)

        viewModel.updateFormList()
        initTabLayout(binding.tabLayoutMain)
    }

    override fun onResume() {
        super.onResume()
    }

    fun initTabLayout(tabLayoutMain: TabLayout) {
        tabLayoutMain.apply {
            var tabIconList = arrayListOf(
                R.drawable.shipment_in_out,
                R.drawable.shipment_inventory,
                R.drawable.region_choose,
                R.drawable.material_search,
                R.drawable.setting,
            )
            var fragments = arrayListOf(
                FormFragment(),
                InventoryFragment(),
                RegionChooseFragment(),
                MaterialSearchFragment(),
                SettingFragment()
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