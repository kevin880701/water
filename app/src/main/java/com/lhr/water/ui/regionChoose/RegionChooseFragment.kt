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
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.map.MapActivity
import com.lhr.water.util.MapPageStatus.MapPage
import com.lhr.water.util.MapPageStatus.RegionPage
import com.lhr.water.util.adapter.RegionChooseAdapter
import timber.log.Timber

class RegionChooseFragment : BaseFragment(), View.OnClickListener, RegionChooseAdapter.Listener {

    private var _binding: FragmentRegionChooseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegionChooseViewModel by viewModels { viewModelFactory }
    lateinit var regionChooseAdapter: RegionChooseAdapter
    var currentRegion = ""
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
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
//        regionRepository.regionList.observe(viewLifecycleOwner) { list ->
//            regionChooseAdapter.submitList(list)
//        }
        viewModel.pageStatus.observe(viewLifecycleOwner) { pageStatus ->
            when(pageStatus){
                RegionPage -> {
                    binding.widgetTitleBar.imageBack.visibility = View.INVISIBLE
                }
                MapPage -> {
                    binding.widgetTitleBar.imageBack.visibility = View.VISIBLE
                }
            }
        }
        viewModel.currentList.observe(viewLifecycleOwner) { newList ->
            regionChooseAdapter.submitList(newList)
        }
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.region_choose)
        initRecyclerView()
    }

    private fun initRecyclerView() {
//        val mapList = ArrayList(resources.getStringArray(R.array.region_array).toList())
//        val mapDataList = mapList.mapIndexed { index, regionName ->
//            regionName
//        }
        regionChooseAdapter = RegionChooseAdapter(this)
        regionChooseAdapter.submitList(viewModel.currentList.value)
        binding.recyclerRegion.adapter = regionChooseAdapter
        binding.recyclerRegion.layoutManager = LinearLayoutManager(activity)

        binding.widgetTitleBar.imageBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageBack ->{
                onBackButtonPressed()
            }
        }
    }

    override fun onItemClick(item: String) {
        when(viewModel.pageStatus.value){
            RegionPage -> {
                currentRegion = item
                viewModel.changeList(item)
                viewModel.pageStatus.value = MapPage
            }
            MapPage -> {
                val intent = Intent(requireActivity(), MapActivity::class.java)
                intent.putExtra("region", currentRegion)
                intent.putExtra("map", item)
                startActivity(intent)
            }
        }
    }


    /**
     * 返回鍵監聽
     */
    private fun onBackButtonPressed() {
        when(viewModel.pageStatus.value){
            RegionPage -> {
                requireActivity().finish()
            }
            MapPage -> {
                viewModel.pageStatus.postValue(RegionPage)
                viewModel.changeList()
                currentRegion = ""
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