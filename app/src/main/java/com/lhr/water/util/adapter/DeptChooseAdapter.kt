package com.lhr.water.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemChooseRegionBinding
import com.lhr.water.room.RegionEntity

class DeptChooseAdapter(val listener: Listener): ListAdapter<RegionEntity, DeptChooseAdapter.ViewHolder>(LOCK_DIFF_UTIL) {

    companion object{
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<RegionEntity>() {
            override fun areItemsTheSame(oldItem: RegionEntity, newItem: RegionEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RegionEntity,
                newItem: RegionEntity
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }


    interface Listener{
        fun onMapSelect(item: RegionEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChooseRegionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemChooseRegionBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listener.onMapSelect(getItem(adapterPosition))
            }
        }

        fun bind(regionEntity: RegionEntity){
            binding.textRegionName.text = regionEntity.deptName
        }
    }
}