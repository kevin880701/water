package com.lhr.water.ui.form.dealMaterial

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lhr.water.R
import com.lhr.water.data.Form
import com.lhr.water.data.Form.Companion.formFromJson
import com.lhr.water.databinding.ActivityDealMaterialBinding
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.isInput
import com.lhr.water.util.viewPager.ViewPageAdapter
import timber.log.Timber


class DealMaterialActivity : BaseActivity(), View.OnClickListener {

    private var _binding: ActivityDealMaterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var jsonString: String
    private lateinit var pageAdapter: ViewPageAdapter
    private lateinit var form: Form
    private var isInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDealMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryBlue, null)

        // 檢查版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            jsonString = intent.getParcelableExtra("jsonString", String::class.java) as String
        } else {
            jsonString = intent.getSerializableExtra("jsonString") as String
        }
        form = formFromJson(jsonString)
        isInput = isInput(form)


        println("==========================================================")
        println("@@@@@@@@@@@@@@@@@@：$isInput")
        print("@@@@@@@@@@@@@@@@@@：$isInput")
        Timber.d("@@@@@@@@@@@@@@@@@@：$isInput")
        println("==========================================================")
        initView()
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = form.formNumber
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        setupBackButton(binding.widgetTitleBar.imageBack)

        initTabLayout(binding.tabLayoutGoods)
    }

    private fun initTabLayout(tabLayoutMain: TabLayout) {
        tabLayoutMain.apply {
            val fragments = arrayListOf(
                WaitDealMaterialFragment(form),
                AlreadyChooseGoodsFragment(form),
            ) as ArrayList<Fragment>
            val tabTextList = arrayListOf(
                if (isInput) getString(R.string.wait_input_material) else getString(R.string.wait_output_material),
                getString(R.string.already_choose_material)
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