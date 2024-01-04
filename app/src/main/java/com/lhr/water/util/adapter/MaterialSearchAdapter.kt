package com.lhr.water.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemMaterialSearchBinding
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.ui.materialSearch.MaterialSearchViewModel

class MaterialSearchAdapter(val viewModel: MaterialSearchViewModel) :
    ListAdapter<StorageContentEntity, MaterialSearchAdapter.ViewHolder>(LOCK_DIFF_UTIL) {

    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<StorageContentEntity>() {
            override fun areItemsTheSame(oldItem: StorageContentEntity, newItem: StorageContentEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StorageContentEntity,
                newItem: StorageContentEntity
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMaterialSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMaterialSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(storageContentEntity: StorageContentEntity) {
            binding.textRegion.text = storageContentEntity.regionName
            binding.textMap.text = storageContentEntity.mapName
            binding.textStorageName.text = storageContentEntity.storageName
            binding.textStorageNumber.text = storageContentEntity.storageName
            binding.textMaterialName.text = storageContentEntity.materialName
            binding.textMaterialNumber.text = storageContentEntity.materialNumber
            binding.textQuantity.text = storageContentEntity.quantity.toString()
        }
    }
}