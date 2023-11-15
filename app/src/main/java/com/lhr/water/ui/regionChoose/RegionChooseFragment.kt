package com.lhr.water.ui.regionChoose

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.FragmentRegionChooseBinding
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.mapChoose.MapChooseActivity
import com.lhr.water.util.recyclerViewAdapter.RegionChooseAdapter

class RegionChooseFragment : BaseFragment(), View.OnClickListener, RegionChooseAdapter.Listener {

    private var _binding: FragmentRegionChooseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegionChooseViewModel by viewModels { viewModelFactory }
    lateinit var regionChooseAdapter: RegionChooseAdapter

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
        viewModel.regionList.observe(viewLifecycleOwner) { list ->
            regionChooseAdapter.submitList(list)
        }
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.region_choose)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val mapList = ArrayList(resources.getStringArray(R.array.region_array).toList())
        regionChooseAdapter = RegionChooseAdapter(this)
        val mapDataList = mapList.mapIndexed { index, regionName ->
            regionName
        }
//        regionAdapter.submitList(viewModel.regionList.value)
        binding.recyclerRegion.adapter = regionChooseAdapter
        binding.recyclerRegion.layoutManager = LinearLayoutManager(activity)

    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

    override fun onItemClick(item: String) {
        val intent = Intent(requireActivity(), MapChooseActivity::class.java)
        intent.putExtra("region", item)
        requireActivity().startActivity(intent)
    }

    override fun onItemLongClick(item: String) {
    }

}