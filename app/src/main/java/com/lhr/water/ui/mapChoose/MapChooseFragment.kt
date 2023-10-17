package com.lhr.water.ui.mapChoose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.ActivityCoverBinding
import com.lhr.water.databinding.FragmentMapChooseBinding
import com.lhr.water.model.FormData
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.login.LoginViewModel
import com.lhr.water.util.ScreenUtils
import com.lhr.water.util.custom.GridSpacingItemDecoration
import com.lhr.water.util.recyclerViewAdapter.MapChooseAdapter
import com.lhr.water.util.recyclerViewAdapter.RegionAdapter
import timber.log.Timber

class MapChooseFragment : BaseFragment(), View.OnClickListener, RegionAdapter.Listener {

    private var _binding: FragmentMapChooseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapChooseViewModel by viewModels({requireParentFragment()}, { viewModelFactory })

    lateinit var mapChooseAdapter: MapChooseAdapter
    lateinit var regionAdapter: RegionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapChooseBinding.inflate(layoutInflater)
//        requireActivity().window.statusBarColor = ResourcesCompat.getColor(resources, R.color.white, null)

        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    fun initRecyclerView() {
        val mapList = ArrayList(resources.getStringArray(R.array.region_array).toList())
        regionAdapter = RegionAdapter(this)
        val mapDataList = mapList.mapIndexed { index, regionName ->
            regionName
        }
        regionAdapter.submitList(mapDataList)
        binding.recyclerRegion.adapter = regionAdapter
        binding.recyclerRegion.layoutManager = LinearLayoutManager(activity)
        binding.recyclerRegion.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dp2px(requireContext(), 8), true))

    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

    override fun onItemClick(item: String) {
//        CamLiveActivity.start(requireActivity(), item.id)
        println("表單點擊")

//        throw RuntimeException("Test Crash") // Force a crash

    }

    override fun onItemLongClick(item: String) {
//        CamLiveActivity.start(requireActivity(), item.id)
        println("表單長按")
    }

}