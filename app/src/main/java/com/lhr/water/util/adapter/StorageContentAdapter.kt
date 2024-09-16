package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.databinding.ItemStorageContentBinding
import com.lhr.water.room.StorageEntity
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.util.convertToRocDate
import com.lhr.water.util.materialStatusMap

class StorageContentAdapter(val listener: Listener, var context: Context): ListAdapter<StorageRecordEntity, StorageContentAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    companion object{
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<StorageRecordEntity>() {
            override fun areItemsTheSame(oldItem: StorageRecordEntity, newItem: StorageRecordEntity): Boolean {
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

    interface Listener{
        fun onMaterialClick(item: StorageRecordEntity)
        fun MaterialTransfer(storageRecordEntity: StorageRecordEntity)
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
                listener.onMaterialClick(getItem(adapterPosition))
            }
        }

        fun bind(storageRecordEntity: StorageRecordEntity){
            binding.textMaterialName.text = storageRecordEntity.materialName
            binding.textInputDate.text = convertToRocDate(storageRecordEntity.recordDate)
            binding.textQuantity.text = storageRecordEntity.quantity
            // 判斷已交貨未驗收
            binding.textStatus.text = materialStatusMap[storageRecordEntity.materialStatus]
            if(storageRecordEntity.materialStatus == "1"){
                binding.linearLayoutMaterial.setBackgroundColor(ContextCompat.getColor(context, R.color.disableGray))
                binding.imageMaterialTransfer.visibility = View.INVISIBLE
            }
            binding.imageMaterialTransfer.setOnClickListener {
                listener.MaterialTransfer(storageRecordEntity)
            }
        }
    }
}