package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.data.form.BaseItem
import com.lhr.water.databinding.ItemWaitDealMaterialBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.ui.form.dealMaterial.DealMaterialViewModel
import com.lhr.water.util.showToast

class WaitDealMaterialAdapter(
    val context: Context,
    val formEntity: FormEntity,
    val listener: Listener,
    val viewModel: DealMaterialViewModel,
    val isInput: Boolean
) :
    ListAdapter<BaseItem, WaitDealMaterialAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<BaseItem>() {
            override fun areItemsTheSame(
                oldItem: BaseItem,
                newItem: BaseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: BaseItem,
                newItem: BaseItem
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    interface Listener {
        fun onItemClick(item: BaseItem, maxQuantity: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWaitDealMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemWaitDealMaterialBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(baseItem: BaseItem) {
            binding.textMaterialName.text =
                baseItem.materialName
//            binding.textMaterialSpec.text =
//                baseItem.materialSpec
//            binding.textMaterialUnit.text =
//                baseItem.materialUnit
            // 1.判斷是進貨還是出貨
            // 2.需判斷暫存待出入庫的貨物列表是否有相對應貨物，有的話需要減去數量
            var quantity = if (isInput) {
                baseItem.requestQuantity.toInt() - viewModel.formRepository.getMaterialQuantityByTempWaitInputGoods(
                    formEntity.reportTitle,
                    formEntity.formNumber,
                    baseItem.materialNumber
                )
            } else {
                baseItem.requestQuantity.toInt() - viewModel.formRepository.getMaterialQuantityByTempWaitInputGoods(
                    formEntity.reportTitle!!,
                    formEntity.formNumber!!,
                    baseItem.materialNumber!!
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