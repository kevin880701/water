package com.lhr.water.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.data.WaitDealGoodsData
import com.lhr.water.databinding.ItemStorageInputBinding

class StorageInputAdapter(val listener: Listener): ListAdapter<WaitDealGoodsData, StorageInputAdapter.ViewHolder>(LOCK_DIFF_UTIL) {

    private val selectedItems = mutableSetOf<Int>()
    companion object{
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<WaitDealGoodsData>() {
            override fun areItemsTheSame(oldItem: WaitDealGoodsData, newItem: WaitDealGoodsData): Boolean {
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


    interface Listener{
        fun onItemClick(item: WaitDealGoodsData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStorageInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class ViewHolder(private val binding: ItemStorageInputBinding): RecyclerView.ViewHolder(binding.root){

        init {
            // bindingAdapterPosition無法使用，所以用adapterPosition替代
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(waitDealGoodsData: WaitDealGoodsData){
            binding.textGoodsName.text = waitDealGoodsData.itemInformation.getString("materialName")
            binding.textGoodsNumber.text = waitDealGoodsData.itemInformation.getString("materialNumber")
            binding.textGoodsNumber.text = waitDealGoodsData.formNumber
            // 設定 CheckBox 的選中狀態
            binding.checkBox.isChecked = selectedItems.contains(adapterPosition)
            // 設定 CheckBox 的點擊事件
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(adapterPosition)
                } else {
                    selectedItems.remove(adapterPosition)
                }
            }
        }
    }

    fun isSelected(position: Int): Boolean {
        return selectedItems.contains(position)
    }
}