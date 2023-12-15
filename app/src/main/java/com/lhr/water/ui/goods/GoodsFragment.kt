package com.lhr.water.ui.goods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lhr.water.R
import com.lhr.water.databinding.FragmentGoodsBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.goods.waitInputGoods.WaitInputGoodsFragment
import com.lhr.water.ui.goods.waitOutputGoods.WaitOutputGoodsFragment
import com.lhr.water.util.viewPager.ViewPageAdapter

class GoodsFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentGoodsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GoodsViewModel by viewModels { viewModelFactory }
    private lateinit var pageAdapter: ViewPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoodsBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.goods_information)

        initTabLayout(binding.tabLayoutGoods)

        binding.widgetTitleBar.imageFilter.setOnClickListener(this)
    }

    private fun initTabLayout(tabLayoutMain: TabLayout) {
        tabLayoutMain.apply {
            val fragments = arrayListOf(
                WaitInputGoodsFragment(),
                WaitOutputGoodsFragment(),
            ) as ArrayList<Fragment>
            val tabTextList = arrayListOf(
                requireActivity().getString(R.string.wait_input_good),
                requireActivity().getString(R.string.wait_output_good)
            )
            pageAdapter = ViewPageAdapter(requireActivity().supportFragmentManager, lifecycle, fragments)
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