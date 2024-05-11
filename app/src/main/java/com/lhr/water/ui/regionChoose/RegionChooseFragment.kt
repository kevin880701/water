package com.lhr.water.ui.regionChoose

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.FragmentRegionChooseBinding
import com.lhr.water.room.RegionEntity
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.util.adapter.DeptChooseAdapter
import com.lhr.water.util.adapter.RegionChooseAdapter
import timber.log.Timber

class RegionChooseFragment : BaseFragment(), View.OnClickListener, RegionChooseAdapter.Listener, DeptChooseAdapter.Listener {

    private var _binding: FragmentRegionChooseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegionChooseViewModel by viewModels { viewModelFactory }
    lateinit var regionChooseAdapter: RegionChooseAdapter
    lateinit var deptChooseAdapter: DeptChooseAdapter
    private val callback = object : OnBackPressedCallback(true /* enabled by default */) {
        override fun handleOnBackPressed() {
                onBackButtonPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegionChooseBinding.inflate(layoutInflater)
//        requireActivity().window.statusBarColor = ResourcesCompat.getColor(resources, R.color.white, null)

        initView()
        bindViewModel()
        return binding.root
    }

    private fun bindViewModel() {
        viewModel.pageStatus.observe(viewLifecycleOwner) { pageStatus ->
            when(pageStatus){
                SelectStatus.RegionPage -> {
                    binding.widgetTitleBar.imageBack.visibility = View.INVISIBLE
                    binding.recyclerRegion.visibility = View.VISIBLE
                    binding.recyclerDept.visibility = View.GONE
                }
                SelectStatus.DeptPage -> {
                    binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
                    binding.recyclerRegion.visibility = View.GONE
                    binding.recyclerDept.visibility = View.VISIBLE
                }
            }
        }

        viewModel.regionList.observe(viewLifecycleOwner) { newList ->
            regionChooseAdapter.submitList(newList)
        }
        viewModel.selectRegion.observe(viewLifecycleOwner) { selectRegion ->
            val filteredList = viewModel.regionRepository.regionEntities.filter { it.deptNumber.startsWith(selectRegion) }
            deptChooseAdapter.submitList(filteredList)
        }
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.region_choose)
        viewModel.regionRepository.filterRegionEntity(viewModel.userRepository.userInfo.deptAno).run {  }
        viewModel.createRegionList()
        initRecyclerView()

        binding.widgetTitleBar.imageBack.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        regionChooseAdapter = RegionChooseAdapter(this)
        regionChooseAdapter.submitList(viewModel.regionList.value)
        binding.recyclerRegion.adapter = regionChooseAdapter
        binding.recyclerRegion.layoutManager = LinearLayoutManager(activity)

        deptChooseAdapter = DeptChooseAdapter(this)
        binding.recyclerDept.adapter = deptChooseAdapter
        binding.recyclerDept.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageBack ->{
                onBackButtonPressed()
            }
        }
    }

    override fun onRegionSelect(regionEntity: RegionEntity) {
        viewModel.pageStatus.value = SelectStatus.DeptPage
        viewModel.selectRegion.postValue(regionEntity.regionNumber)
    }

    override fun onMapSelect(regionEntity: RegionEntity) {
        val intent = Intent(requireActivity(), MapActivity::class.java)
        intent.putExtra("regionEntity", regionEntity)
        startActivity(intent)
    }


    /**
     * 返回鍵監聽
     */
    private fun onBackButtonPressed() {
        when(viewModel.pageStatus.value){
            SelectStatus.RegionPage -> {
                requireActivity().finish()
            }
            SelectStatus.DeptPage -> {
                viewModel.pageStatus.postValue(SelectStatus.RegionPage)
            }
            else -> {
                requireActivity().finish()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
        callback.remove()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}