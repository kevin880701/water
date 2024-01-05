package com.lhr.water.ui.history.dealMaterial

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lhr.water.R
import com.lhr.water.databinding.ActivityDealMaterialBinding
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.manager.jsonStringToJson
import com.lhr.water.util.viewPager.ViewPageAdapter
import org.json.JSONObject


class DealMaterialActivity : BaseActivity(), View.OnClickListener {

    private var _binding: ActivityDealMaterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var jsonString: JSONObject
    private lateinit var pageAdapter: ViewPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDealMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)

        // 檢查版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            jsonString = jsonStringToJson(intent.getParcelableExtra("jsonString", String::class.java) as String)
        } else {
            jsonString = jsonStringToJson(intent.getSerializableExtra("jsonString") as String)
        }

        initView()
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = jsonString.getString("formNumber")
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        setupBackButton(binding.widgetTitleBar.imageBack)

        initTabLayout(binding.tabLayoutGoods)
    }

    private fun initTabLayout(tabLayoutMain: TabLayout) {
        tabLayoutMain.apply {
            val fragments = arrayListOf(
                WaitDealMaterialFragment(jsonString),
                AlreadyChooseGoodsFragment(jsonString),
            ) as ArrayList<Fragment>
            val tabTextList = arrayListOf(
                getString(R.string.wait_input_good),
                getString(R.string.already_choose_good)
            )
            pageAdapter = ViewPageAdapter(supportFragmentManager, lifecycle, fragments)
            binding.viewPagerGoods.adapter = pageAdapter
            TabLayoutMediator(this, binding.viewPagerGoods) { tab, position ->
                tab.text = tabTextList[position]
            }.attach()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }
}