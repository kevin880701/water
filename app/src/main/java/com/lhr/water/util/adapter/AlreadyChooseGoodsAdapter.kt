package com.lhr.water.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.data.TempDealGoodsData
import com.lhr.water.databinding.ItemAlreadyChooseGoodsBinding
import com.lhr.water.util.manager.jsonStringToJson

class AlreadyChooseGoodsAdapter(
    val listener: Listener,
) :
    ListAdapter<TempDealGoodsData, AlreadyChooseGoodsAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<TempDealGoodsData>() {
            override fun areItemsTheSame(
                oldItem: TempDealGoodsData,
                newItem: TempDealGoodsData
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TempDealGoodsData,
                newItem: TempDealGoodsData
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    interface Listener {
        fun onRemoveClick(item: TempDealGoodsData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAlreadyChooseGoodsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemAlreadyChooseGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tempDealGoodsData: TempDealGoodsData) {
            binding.textMaterialName.text = tempDealGoodsData.itemDetail.materialName
            binding.textMaterialNumber.text = tempDealGoodsData.itemDetail.materialNumber
            binding.textRegion.text = tempDealGoodsData.regionName
            binding.textMap.text = tempDealGoodsData.mapName
            binding.textStorage.text = tempDealGoodsData.storageName
            binding.textQuantity.text = tempDealGoodsData.quantity.toString()

            binding.imageRemove.setOnClickListener {
                listener.onRemoveClick(getItem(adapterPosition))
            }
        }
    }
}