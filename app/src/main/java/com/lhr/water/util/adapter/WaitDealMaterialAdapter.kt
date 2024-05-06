package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.data.Form
import com.lhr.water.data.ItemDetail
import com.lhr.water.databinding.ItemWaitDealMaterialBinding
import com.lhr.water.ui.form.FormViewModel
import com.lhr.water.util.showToast

class WaitDealMaterialAdapter(
    val context: Context,
    val form: Form,
    val listener: Listener,
    val viewModel: FormViewModel,
    val isInput: Boolean
) :
    ListAdapter<ItemDetail, WaitDealMaterialAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<ItemDetail>() {
            override fun areItemsTheSame(
                oldItem: ItemDetail,
                newItem: ItemDetail
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemDetail,
                newItem: ItemDetail
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    interface Listener {
        fun onItemClick(item: ItemDetail, maxQuantity: String)
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

        fun bind(itemDetail: ItemDetail) {
            binding.textMaterialName.text =
                itemDetail.materialName
            binding.textMaterialNumber.text =
                itemDetail.materialNumber
            binding.textMaterialSpec.text =
                itemDetail.materialSpec
            binding.textMaterialUnit.text =
                itemDetail.materialUnit
            // 1.判斷是進貨還是出貨
            // 2.需判斷暫存待出入庫的貨物列表是否有相對應貨物，有的話需要減去數量
            var quantity = if (isInput) {
                itemDetail.receivedQuantity!! - viewModel.formRepository.getMaterialQuantityByTempWaitInputGoods(
                    form.reportTitle!!,
                    form.formNumber!!,
                    itemDetail.materialNumber!!
                )
            } else {

                itemDetail.actualQuantity!! - viewModel.formRepository.getMaterialQuantityByTempWaitOutputGoods(
                    form.reportTitle!!,
                    form.formNumber!!,
                    itemDetail.materialNumber!!
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