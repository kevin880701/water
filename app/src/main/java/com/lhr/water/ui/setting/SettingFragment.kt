package com.lhr.water.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.FragmentRegionChooseBinding
import com.lhr.water.databinding.FragmentSettingBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.mapChoose.MapChooseActivity
import com.lhr.water.util.recyclerViewAdapter.RegionChooseAdapter

class SettingFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(layoutInflater)

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
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.setting)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}