package com.lhr.water.ui.form

import android.content.Intent
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
import com.lhr.water.ui.formContent.FormContentActivity
import com.lhr.water.ui.mapChoose.MapChooseActivity
import com.lhr.water.ui.qrCode.QrcodeActivity
import com.lhr.water.util.ScreenUtils
import com.lhr.water.util.custom.GridSpacingItemDecoration
import com.lhr.water.util.recyclerViewAdapter.FormAdapter

class FormFragment : BaseFragment(), View.OnClickListener, FormAdapter.Listener {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel:FormViewModel by viewModels{ viewModelFactory }

    lateinit var formAdapter: FormAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormBinding.inflate(layoutInflater)
        requireActivity().window.statusBarColor = ResourcesCompat.getColor(resources, R.color.seed, null)

        binding.textUser.text = "Hello, XXX"

        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        formAdapter = FormAdapter(this)
        val imageList = listOf(
            R.drawable.delivery,
            R.drawable.check,
            R.drawable.take_goods,
            R.drawable.allocate,
            R.drawable.return_goods
        )
        val formList = ArrayList(resources.getStringArray(R.array.form_array).toList())
        val formDataList = formList.mapIndexed { index, formName ->
            FormData(
                id = index.toString(),
                formName = formName,
                formNumber = index,
                formImage = imageList[index]
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
        val intent = Intent(requireActivity(), FormContentActivity::class.java)
        intent.putExtra("formName", item.formName)
        requireActivity().startActivity(intent)
    }

    override fun onItemLongClick(item: FormData) {
        println("長按")
    }

}