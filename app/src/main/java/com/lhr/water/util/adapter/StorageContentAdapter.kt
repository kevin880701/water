package com.lhr.water.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemStorageContentBinding
import com.lhr.water.room.StorageContentEntity
import com.lhr.water.util.manager.jsonStringToJson

class StorageContentAdapter(val listener: Listener): ListAdapter<StorageContentEntity, StorageContentAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    companion object{
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

    interface Listener{
        fun onItemClick(item: StorageContentEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStorageContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class ViewHolder(private val binding: ItemStorageContentBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(storageContentEntity: StorageContentEntity){
            binding.textMaterialName.text = storageContentEntity.materialName
            binding.textMaterialNumber.text = storageContentEntity.materialNumber
            binding.textMaterialSpec.text = storageContentEntity.materialSpec
            binding.textMaterialUnit.text = storageContentEntity.materialUnit
            binding.textQuantity.text = storageContentEntity.quantity.toString()
        }
    }
}