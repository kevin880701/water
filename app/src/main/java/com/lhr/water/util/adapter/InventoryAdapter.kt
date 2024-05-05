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
import com.lhr.water.data.InventoryForm
import com.lhr.water.data.InventoryForm.Companion.toJsonString
import com.lhr.water.databinding.ItemInventoryMaterialBinding
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.InventoryEntity
import com.lhr.water.room.SqlDatabase

class InventoryAdapter(val listener: Listener, context: Context) :
    ListAdapter<InventoryForm, InventoryAdapter.ViewHolder>(LOCK_DIFF_UTIL) {
    var context = context

    companion object {
        val LOCK_DIFF_UTIL = object : DiffUtil.ItemCallback<InventoryForm>() {
            override fun areItemsTheSame(oldItem: InventoryForm, newItem: InventoryForm): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: InventoryForm,
                newItem: InventoryForm
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

        fun bind(inventoryForm: InventoryForm) {
            binding.textMaterialName.text = inventoryForm.materialName
            binding.textMaterialSpec.text = inventoryForm.materialSpec
            binding.textMaterialUnit.text = inventoryForm.materialUnit
            binding.textQuantity.text = Editable.Factory.getInstance().newEditable(inventoryForm.actualQuantity.toString())


            binding.root.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }

            binding.imageEdit.setOnClickListener {
                binding.imageEdit.visibility = View.GONE
                binding.imageOk.visibility = View.VISIBLE
                binding.textQuantity.isEnabled = true
                binding.textQuantity.isEnabled = true
                binding.textQuantity.setBackgroundColor(Color.LTGRAY)
            }

            binding.imageOk.setOnClickListener {
                binding.imageEdit.visibility = View.VISIBLE
                binding.imageOk.visibility = View.GONE
                binding.textQuantity.isEnabled = false
                binding.textQuantity.setBackgroundColor(Color.TRANSPARENT)
                inventoryForm.actualQuantity = binding.textQuantity.text.toString().toInt()

                var tempInventoryEntity = InventoryEntity()
                tempInventoryEntity.formNumber = inventoryForm.formNumber.toString()
                tempInventoryEntity.formContent = inventoryForm.toJsonString()
                SqlDatabase.getInstance().getInventoryDao().insertOrUpdate(tempInventoryEntity)
                FormRepository.getInstance(context).loadInventoryForm()
            }
        }
    }

    interface Listener {
        fun onItemClick(inventoryForm: InventoryForm)
    }
}