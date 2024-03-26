package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.ItemWaitDealMaterialBinding
import com.lhr.water.ui.form.FormViewModel
import com.lhr.water.util.showToast

class WaitDealMaterialAdapter(
    val context: Context,
    val reportTitle: String,
    val formNumber: String,
    val listener: Listener,
    val viewModel: FormViewModel,
    val isInput: Boolean
) :
    ListAdapter<WaitDealGoodsData, WaitDealMaterialAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<WaitDealGoodsData>() {
            override fun areItemsTheSame(
                oldItem: WaitDealGoodsData,
                newItem: WaitDealGoodsData
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: WaitDealGoodsData,
                newItem: WaitDealGoodsData
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    interface Listener {
        fun onItemClick(item: WaitDealGoodsData, maxQuantity: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWaitDealMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemWaitDealMaterialBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(waitDealGoodsData: WaitDealGoodsData) {
            binding.textMaterialName.text =
                waitDealGoodsData.itemDetail.materialName
            binding.textMaterialNumber.text =
                waitDealGoodsData.itemDetail.materialNumber
            binding.textMaterialSpec.text =
                waitDealGoodsData.itemDetail.materialSpec
            binding.textMaterialUnit.text =
                waitDealGoodsData.itemDetail.materialUnit
            // 1.判斷是進貨還是出貨
            // 2.需判斷暫存待出入庫的貨物列表是否有相對應貨物，有的話需要減去數量
            var quantity = if (isInput) {
                waitDealGoodsData.itemDetail.receivedQuantity!! - viewModel.formRepository.getMaterialQuantityByTempWaitInputGoods(
                    reportTitle,
                    formNumber,
                    waitDealGoodsData.itemDetail.number.toString()
                )
            } else {
                println("reportTitle：${reportTitle}")
                println("formNumber：${formNumber}")
                println("waitDealGoodsData.itemDetail.number.toString()：${waitDealGoodsData.itemDetail.number.toString()}")
                println("waitDealGoodsData.itemDetail.actualQuantity：${waitDealGoodsData.itemDetail.actualQuantity}")
                println("waitDealGoodsData.itemDetail.number.toString()：${waitDealGoodsData.itemDetail.number.toString()}")
                println("被減掉：${viewModel.formRepository.getMaterialQuantityByTempWaitOutputGoods(
                    reportTitle,
                    formNumber,
                    waitDealGoodsData.itemDetail.number.toString()
                )}")

                waitDealGoodsData.itemDetail.actualQuantity!! - viewModel.formRepository.getMaterialQuantityByTempWaitOutputGoods(
                    reportTitle,
                    formNumber,
                    waitDealGoodsData.itemDetail.number.toString()
                )
            }

            binding.textQuantity.text = quantity.toString()
            if (quantity == 0) {
                binding.cover.visibility = View.VISIBLE
            } else {
                binding.cover.visibility = View.INVISIBLE
            }

            binding.root.setOnClickListener {

                if (quantity == 0) {
                    showToast(context, "已經無貨物")
                } else {
                    listener.onItemClick(
                        getItem(adapterPosition),
                        binding.textQuantity.text.toString()
                    )
                }
            }
        }
    }
}