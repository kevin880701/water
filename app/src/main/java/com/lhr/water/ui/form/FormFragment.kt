package com.lhr.water.ui.form

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.databinding.FragmentFormBinding
import com.lhr.water.util.recyclerViewAdapter.FormAdapter

class FormFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentFormBinding
    lateinit var viewModel: FormViewModel
    lateinit var formAdapter: FormAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_form, container, false
        )
        val view: View = binding!!.root

        viewModel = ViewModelProvider(
            this,
            FormViewModelFactory(requireActivity().application)
        )[FormViewModel::class.java]
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        binding.textUser.text = "Hello, Anna"
//        viewModel.title.postValue(this.requireContext().resources.getString(R.string.form))

//        initRecyclerView(binding.recyclerForm)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun initRecyclerView(recyclerView: RecyclerView) {


//        binding.recyclerPurchase.adapter = lockerAdapter
//        binding.recyclerPurchase.layoutManager = GridLayoutManager(requireContext(), 3)
//        binding.recyclerPurchase.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dp2px(requireContext(), 8), true))
//
//        binding.recyclerShipping.adapter = camAdapter
//        binding.recyclerShipping.layoutManager = GridLayoutManager(requireContext(), 3)
//        binding.recyclerShipping.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dp2px(requireContext(), 8), true))
//
//        binding.recyclerOthers.adapter = plugAdapter
//        binding.recyclerOthers.layoutManager = GridLayoutManager(requireContext(), 3)
//        binding.recyclerOthers.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dp2px(requireContext(), 8), true))

    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

}