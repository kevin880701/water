package com.lhr.water.ui.mapChoose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.databinding.FragmentMapChooseBinding
import com.lhr.water.util.recyclerViewAdapter.MapChooseAdapter

class MapChooseFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentMapChooseBinding
    lateinit var viewModel: MapChooseViewModel
    lateinit var mapChooseAdapter: MapChooseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_map_choose, container, false
        )
        val view: View = binding!!.root

        viewModel = ViewModelProvider(
            this,
            MapChooseViewModelFactory(requireActivity().application)
        )[MapChooseViewModel::class.java]
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        viewModel.title.postValue(this.requireContext().resources.getString(R.string.map))
        initRecyclerView(binding.recyclerMap)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    fun initRecyclerView(recyclerView: RecyclerView) {
        val mapList = ArrayList(resources.getStringArray(R.array.map_array).toList())
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        mapChooseAdapter = MapChooseAdapter(mapList, this)
        recyclerView.adapter = mapChooseAdapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

}