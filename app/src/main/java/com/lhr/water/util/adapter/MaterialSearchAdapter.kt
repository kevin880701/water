package com.lhr.water.util.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.R
import com.lhr.water.databinding.ItemMaterialSearchBinding
import com.lhr.water.repository.RegionRepository
import com.lhr.water.room.StorageRecordEntity
import com.lhr.water.ui.materialSearch.MaterialSearchViewModel
import com.lhr.water.util.materialStatusMap

class MaterialSearchAdapter(val viewModel: MaterialSearchViewModel,
                            val context: Context,) :
    ListAdapter<StorageRecordEntity, MaterialSearchAdapter.ViewHolder>(LOCK_DIFF_UTIL) {

    val regionRepository by lazy { RegionRepository.getInstance(context) }

    companion object {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMaterialSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMaterialSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(storageRecordEntity: StorageRecordEntity) {
            val storageEntity = regionRepository.findStorageEntityByStorageId(storageRecordEntity.storageId)
            val regionEntity = regionRepository.findRegionEntityByDeptNumberAndMapSeq(storageEntity.deptNumber, storageEntity.mapSeq)

            binding.textRegion.text = regionEntity.regionName
            binding.textDept.text = "${regionEntity.deptName}-${regionEntity.mapSeq}"
            binding.textStorageName.text = storageEntity.storageName

            binding.textMaterialName.text = storageRecordEntity.materialName
            binding.textMaterialNumber.text = storageRecordEntity.materialNumber
            binding.textQuantity.text = storageRecordEntity.quantity

            // 判斷已交貨未驗收
            binding.textMaterialStatus.text = materialStatusMap[storageRecordEntity.materialStatus]
            if(storageRecordEntity.materialStatus == "1"){
                binding.linearItem.setBackgroundColor(ContextCompat.getColor(context, R.color.disableGray))
            }
        }
    }
}