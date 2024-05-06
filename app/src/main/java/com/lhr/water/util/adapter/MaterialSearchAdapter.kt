package com.lhr.water.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemMaterialSearchBinding
import com.lhr.water.room.CheckoutEntity
import com.lhr.water.ui.materialSearch.MaterialSearchViewModel

class MaterialSearchAdapter(val viewModel: MaterialSearchViewModel) :
    ListAdapter<CheckoutEntity, MaterialSearchAdapter.ViewHolder>(LOCK_DIFF_UTIL) {

    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<CheckoutEntity>() {
            override fun areItemsTheSame(oldItem: CheckoutEntity, newItem: CheckoutEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CheckoutEntity,
                newItem: CheckoutEntity
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

        fun bind(checkoutEntity: CheckoutEntity) {
            binding.textStorageName.text = checkoutEntity.storageId.toString()
            binding.textMaterialName.text = checkoutEntity.materialName
            binding.textMaterialNumber.text = checkoutEntity.materialNumber
            binding.textQuantity.text = checkoutEntity.quantity.toString()
        }
    }
}