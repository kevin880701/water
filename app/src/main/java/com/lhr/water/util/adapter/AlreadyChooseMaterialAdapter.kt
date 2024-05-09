package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemAlreadyChooseGoodsBinding
import com.lhr.water.repository.RegionRepository
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.util.convertToRocDate

class AlreadyChooseMaterialAdapter(
    val listener: Listener,
    val context: Context,
) :
    ListAdapter<StorageRecordEntity, AlreadyChooseMaterialAdapter.ViewHolder>(LOCK_DIFF_UTIL) {

    val regionRepository by lazy { RegionRepository.getInstance(context) }
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

        fun bind(storageRecordEntity: StorageRecordEntity) {
            val storageEntity = regionRepository.findStorageEntityByStorageId(storageRecordEntity.storageId)
            val regionEntity = regionRepository.findRegionEntityByDeptNumberAndMapSeq(storageEntity.deptNumber, storageEntity.mapSeq)
            binding.textMaterialName.text = storageRecordEntity.materialName
            binding.textInputDate.text = convertToRocDate(storageRecordEntity.inputTime)
            binding.textRegion.text = regionEntity.regionName
            binding.textDept.text = "${regionEntity.deptName}-${regionEntity.mapSeq}"
            binding.textStorage.text = storageEntity.storageName
            binding.textQuantity.text = storageRecordEntity.quantity.toString()

            binding.imageRemove.setOnClickListener {
                listener.onRemoveClick(getItem(adapterPosition))
            }
        }
    }
}