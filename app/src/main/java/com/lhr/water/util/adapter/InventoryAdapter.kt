package com.lhr.water.util.adapter

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemInventoryMaterialBinding
import com.lhr.water.room.InventoryEntity
import com.lhr.water.room.SqlDatabase

class InventoryAdapter(val listener: Listener, context: Context) :
    ListAdapter<InventoryEntity, InventoryAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    var context = context

    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<InventoryEntity>() {
            override fun areItemsTheSame(oldItem: InventoryEntity, newItem: InventoryEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: InventoryEntity,
                newItem: InventoryEntity
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemInventoryMaterialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemInventoryMaterialBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(inventoryEntity: InventoryEntity) {
            binding.textDept.text = inventoryEntity.deptName
            binding.textMaterialName.text = inventoryEntity.materialName
            binding.editQuantity.text = Editable.Factory.getInstance().newEditable(inventoryEntity.actualQuantity.toString())
            binding.textDefaultQuantity.text = Editable.Factory.getInstance().newEditable(inventoryEntity.defaultQuantity.toString())


            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }

            binding.imageEdit.setOnClickListener {
                binding.imageEdit.visibility = View.GONE
                binding.imageOk.visibility = View.VISIBLE
                binding.editQuantity.isEnabled = true
                binding.editQuantity.setBackgroundColor(Color.LTGRAY)
            }

            binding.imageOk.setOnClickListener {
                binding.imageEdit.visibility = View.VISIBLE
                binding.imageOk.visibility = View.GONE
                binding.editQuantity.isEnabled = false
                binding.editQuantity.setBackgroundColor(Color.TRANSPARENT)
                inventoryEntity.actualQuantity = binding.editQuantity.text.toString()
                inventoryEntity.dealStatus = "處理完成"
                inventoryEntity.isUpdate = false
                SqlDatabase.getInstance().getInventoryDao().insertOrUpdate(inventoryEntity)
            }
        }
    }

    interface Listener {
        fun onItemClick(InventoryEntity: InventoryEntity)
    }
}