package com.lhr.water.ui.form

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.FragmentFormBinding
import com.lhr.water.model.FormData
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.util.ScreenUtils
import com.lhr.water.util.custom.GridSpacingItemDecoration
import com.lhr.water.util.recyclerViewAdapter.FormAdapter

class FormFragment : BaseFragment(), View.OnClickListener, FormAdapter.Listener {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel:FormViewModel by viewModels({requireParentFragment()}, { viewModelFactory })

    lateinit var formAdapter: FormAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormBinding.inflate(layoutInflater)
        requireActivity().window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)

        binding.textUser.text = "Hello, Anna"

        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        formAdapter = FormAdapter(this)
        val formList = ArrayList(resources.getStringArray(R.array.form_array).toList())
        val formDataList = formList.mapIndexed { index, formName ->
            FormData(
                id = index.toString(),
                formName = formName,
                formNumber = index
            )
        }
        formAdapter.submitList(formDataList)
        binding.recyclerForm.adapter = formAdapter
        binding.recyclerForm.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerForm.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dp2px(requireContext(), 8), true))
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

    override fun onItemClick(item: FormData) {
//        CamLiveActivity.start(requireActivity(), item.id)
        println("表單點擊")

//        throw RuntimeException("Test Crash") // Force a crash

    }

    override fun onItemLongClick(item: FormData) {
//        CamLiveActivity.start(requireActivity(), item.id)
        println("表單長按")
    }

}