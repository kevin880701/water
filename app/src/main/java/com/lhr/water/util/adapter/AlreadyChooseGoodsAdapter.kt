package com.lhr.water.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemAlreadyChooseGoodsBinding
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.history.HistoryViewModel
import com.lhr.water.util.manager.jsonStringToJson

class AlreadyChooseGoodsAdapter(
    val listener: Listener,
    val historyViewModel: HistoryViewModel

) :
    ListAdapter<StorageRecordEntity, AlreadyChooseGoodsAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<StorageRecordEntity>() {
            override fun areItemsTheSame(
                oldItem: StorageRecordEntity,
                newItem: StorageRecordEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StorageRecordEntity,
                newItem: StorageRecordEntity
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    interface Listener {
        fun onRemoveClick(item: StorageRecordEntity)
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

        fun bind(storageContentEntity: StorageRecordEntity) {
            binding.textMaterialName.text =
                jsonStringToJson(storageContentEntity.itemInformation)["materialName"].toString()
            binding.textMaterialNumber.text =
                jsonStringToJson(storageContentEntity.itemInformation)["materialNumber"].toString()
            binding.textRegion.text = storageContentEntity.regionName
            binding.textMap.text = storageContentEntity.mapName
            binding.textStorage.text = storageContentEntity.storageName
            binding.textQuantity.text =
                jsonStringToJson(storageContentEntity.itemInformation)["quantity"].toString()


            binding.imageRemove.setOnClickListener {
                listener.onRemoveClick(getItem(adapterPosition))
            }
        }
    }
}