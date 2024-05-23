package com.lhr.water.ui.form.dealMaterial

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lhr.water.R
import com.lhr.water.data.form.BaseForm
import com.lhr.water.databinding.ActivityDealMaterialBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.util.isInput
import com.lhr.water.util.viewPager.ViewPageAdapter

class DealMaterialActivity : BaseActivity(), View.OnClickListener {

    private var _binding: ActivityDealMaterialBinding? = null
    private val binding get() = _binding!!
    private lateinit var pageAdapter: ViewPageAdapter
    private lateinit var formEntity: FormEntity
    private lateinit var baseForm: BaseForm
    private var isInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDealMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ResourcesCompat.getColor(resources, R.color.primaryBlue, null)


        formEntity = intent.getSerializableExtra("formEntity") as FormEntity
        baseForm = formEntity.parseBaseForm()

        isInput = isInput(formEntity)

        initView()
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = formEntity.formNumber
        binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
        setupBackButton(binding.widgetTitleBar.imageBack)

        initTabLayout(binding.tabLayoutGoods)
    }

    private fun initTabLayout(tabLayoutMain: TabLayout) {
        tabLayoutMain.apply {
            val fragments = arrayListOf(
                WaitDealMaterialFragment(formEntity),
                AlreadyChooseGoodsFragment(formEntity),
            ) as ArrayList<Fragment>
            val tabTextList = arrayListOf(
                if (baseForm.isInput()) getString(R.string.wait_input_material) else getString(R.string.wait_output_material),
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