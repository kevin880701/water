package com.lhr.water.ui.form

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
        viewModel.title.postValue(this.requireContext().resources.getString(R.string.form))

        initRecyclerView(binding.recyclerForm)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun initRecyclerView(recyclerView: RecyclerView) {

        val formList = ArrayList(resources.getStringArray(R.array.form_array).toList())
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        formAdapter = FormAdapter(formList)
        recyclerView.adapter = formAdapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

}