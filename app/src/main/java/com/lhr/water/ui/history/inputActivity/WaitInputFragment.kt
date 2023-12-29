package com.lhr.water.ui.history.inputActivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.ActivityInputBinding
import com.lhr.water.databinding.FragmentWaitInputGoodsBinding
import com.lhr.water.model.Model
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.BaseActivity
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.adapter.HistoryAdapter
import com.lhr.water.util.adapter.InputAdapter
import com.lhr.water.util.adapter.WaitOutputGoodsAdapter
import com.lhr.water.util.dialog.InputDialog
import com.lhr.water.util.dialog.InputGoodsDialog
import com.lhr.water.util.manager.jsonStringToJson
import org.json.JSONObject


class WaitInputFragment(jsonString: JSONObject) : BaseFragment(), View.OnClickListener, InputAdapter.Listener, InputDialog.Listener {

    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentWaitInputGoodsBinding? = null
    private val binding get() = _binding!!
    private lateinit var inputAdapter: InputAdapter
    private var jsonString = jsonString

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWaitInputGoodsBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.formRepository.tempWaitInputGoods.observe(viewLifecycleOwner) { newList ->
            inputAdapter.notifyDataSetChanged()
        }
    }

    private fun initView() {
        initRecyclerView()
    }


    private fun initRecyclerView() {
        inputAdapter = InputAdapter(requireContext(), jsonString["reportTitle"].toString(),
            jsonString["formNumber"].toString(),this, viewModel)
        inputAdapter.submitList(
            viewModel.filterWaitOutputGoods(
                jsonString["reportTitle"].toString(),
                jsonString["formNumber"].toString()
            )
        )
        binding.recyclerGoods.adapter = inputAdapter
        binding.recyclerGoods.layoutManager = LinearLayoutManager(this@WaitInputFragment.requireActivity())
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.linearLayoutGoodInput ->{

            }
        }
    }

    override fun onItemClick(waitDealGoodsData: WaitDealGoodsData, maxQuantity: String) {
        val goodsDialog = InputDialog(
            waitDealGoodsData,
            listener = this,
            maxQuantity
        )
        goodsDialog.show(requireActivity().supportFragmentManager, "InputGoodsDialog")
    }

    override fun onGoodsDialogConfirm(formItemJson: JSONObject) {
        TODO("Not yet implemented")
    }
}