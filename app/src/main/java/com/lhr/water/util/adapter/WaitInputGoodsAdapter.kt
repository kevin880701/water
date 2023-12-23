package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.ItemWaitInputGoodsBinding
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.goods.GoodsViewModel
import com.lhr.water.util.manager.jsonStringToJson

class WaitInputGoodsAdapter(
    val listener: Listener,
    val viewModel: GoodsViewModel,
    val context: Context
) :
    ListAdapter<WaitDealGoodsData, WaitInputGoodsAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
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
        fun onItemClick(item: WaitDealGoodsData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWaitInputGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemWaitInputGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(waitDealGoodsData: WaitDealGoodsData) {
            binding.textFormName.text = waitDealGoodsData.reportTitle
            binding.textFormNumber.text = waitDealGoodsData.formNumber
            binding.textMaterialName.text =
                waitDealGoodsData.itemInformation["materialName"].toString()
            binding.textMaterialNumber.text =
                waitDealGoodsData.itemInformation["materialNumber"].toString()
            if (viewModel.formRepository.isInTempWaitInputGoods(
                    waitDealGoodsData.formNumber,
                    waitDealGoodsData.reportTitle,
                    waitDealGoodsData.itemInformation["materialName"].toString(),
                    waitDealGoodsData.itemInformation["materialNumber"].toString(),
                    waitDealGoodsData.itemInformation["itemNo"].toString()
                )
            ) {
                binding.textStatus.text = context.getString(R.string.already_choose)
                binding.textStatus.setTextColor(context.getColor(R.color.green))
            } else {
                binding.textStatus.text = context.getString(R.string.not_choose)
                binding.textStatus.setTextColor(context.getColor(R.color.red))
            }
        }
    }
}