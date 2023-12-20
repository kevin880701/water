package com.lhr.water.util.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.DialogOutputGoodsBinding
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.base.APP
import com.lhr.water.ui.base.AppViewModelFactory
import com.lhr.water.ui.goods.GoodsViewModel
import com.lhr.water.util.adapter.WaitOutputGoodsAdapter
import com.lhr.water.util.adapter.WaitOutputGoodsStorageAdapter
import org.json.JSONObject

class OutputGoodsDialog(
    waitDealGoodsData: WaitDealGoodsData,
    listener: Listener
) : DialogFragment(), View.OnClickListener, WaitOutputGoodsStorageAdapter.Listener {

    private var dialog: AlertDialog? = null
    private var waitDealGoodsData = waitDealGoodsData
    private var listener = listener
    private var _binding: DialogOutputGoodsBinding? = null
    private val binding get() = _binding!!
    var materialName = ""
    var materialNumber = ""
    private lateinit var waitOutputGoodsStorageAdapter: WaitOutputGoodsStorageAdapter

    private val viewModelFactory: AppViewModelFactory
        get() = (requireContext().applicationContext as APP).appContainer.viewModelFactory
    private val viewModel: GoodsViewModel by viewModels { viewModelFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogOutputGoodsBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)

        initView()
        builder.setView(binding.root)
        dialog = builder.create()
        return builder.create()
    }

    fun initView() {
        binding.widgetTitleBar.textTitle.text =
            activity?.resources?.getString(R.string.goods_information)
        binding.widgetTitleBar.imageCancel.visibility = View.VISIBLE
        initRecyclerView()

        binding.buttonConfirm.setOnClickListener(this)
        binding.widgetTitleBar.imageCancel.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        waitOutputGoodsStorageAdapter = WaitOutputGoodsStorageAdapter(this)
        waitOutputGoodsStorageAdapter.submitList(viewModel.getOutputGoodsWhere(waitDealGoodsData.itemInformation.optString("materialName"),waitDealGoodsData.itemInformation.optString("materialNumber")))
        binding.recyclerGoods.adapter = waitOutputGoodsStorageAdapter
        binding.recyclerGoods.layoutManager = LinearLayoutManager(activity)
    }

    interface Listener {
        fun onGoodsDialogConfirm(formItemJson: JSONObject)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonConfirm -> {
                this.dismiss()
            }

            R.id.imageCancel -> {
                this.dismiss()
            }
        }
    }

    override fun onItemClick(item: StorageContentEntity) {

    }
}