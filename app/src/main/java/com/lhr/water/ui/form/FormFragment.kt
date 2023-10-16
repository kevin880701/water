package com.lhr.water.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.lhr.water.R
import com.lhr.water.databinding.FragmentFormBinding
import com.lhr.water.model.FormData
import com.lhr.water.util.ScreenUtils
import com.lhr.water.util.custom.GridSpacingItemDecoration
import com.lhr.water.util.recyclerViewAdapter.PurchaseFormAdapter

class FormFragment : Fragment(), View.OnClickListener, PurchaseFormAdapter.Listener {
    lateinit var binding: FragmentFormBinding
    lateinit var viewModel: FormViewModel
    lateinit var purchaseAdapter: PurchaseFormAdapter
    lateinit var shippingAdapter: PurchaseFormAdapter
    lateinit var othersAdapter: PurchaseFormAdapter

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

        initRecyclerView()
        return view
    }

    private fun initRecyclerView() {
        purchaseAdapter = PurchaseFormAdapter(this)
        val mapList = ArrayList(resources.getStringArray(R.array.purchase_array).toList())
        val formDataList = mapList.mapIndexed { index, formName ->
            FormData(
                id = index.toString(),
                formName = formName,
                formNumber = index
            )
        }
        purchaseAdapter.submitList(formDataList)
        binding.recyclerPurchase.adapter = purchaseAdapter
        binding.recyclerPurchase.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerPurchase.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dp2px(requireContext(), 8), true))


        shippingAdapter = PurchaseFormAdapter(this)
        val shippingList = ArrayList(resources.getStringArray(R.array.shipping_array).toList())
        val shippingDataList = shippingList.mapIndexed { index, formName ->
            FormData(
                id = index.toString(),
                formName = formName,
                formNumber = index
            )
        }
        shippingAdapter.submitList(shippingDataList)
        binding.recyclerShipping.adapter = shippingAdapter
        binding.recyclerShipping.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerShipping.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dp2px(requireContext(), 8), true))

        othersAdapter = PurchaseFormAdapter(this)
        val othersList = ArrayList(resources.getStringArray(R.array.others_array).toList())
        val othersDataList = othersList.mapIndexed { index, formName ->
            FormData(
                id = index.toString(),
                formName = formName,
                formNumber = index
            )
        }
        othersAdapter.submitList(othersDataList)
        binding.recyclerOthers.adapter = othersAdapter
        binding.recyclerOthers.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerOthers.addItemDecoration(GridSpacingItemDecoration(3, ScreenUtils.dp2px(requireContext(), 8), true))

    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

    override fun onItemClick(item: FormData) {
//        CamLiveActivity.start(requireActivity(), item.id)
        println("表單點擊")
    }

    override fun onItemLongClick(item: FormData) {
//        CamLiveActivity.start(requireActivity(), item.id)
        println("表單長按")
    }

}