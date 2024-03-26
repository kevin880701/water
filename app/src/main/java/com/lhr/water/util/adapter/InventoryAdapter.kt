package com.lhr.water.util.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lhr.water.databinding.ItemInventoryMaterialBinding
import com.lhr.water.repository.FormRepository
import com.lhr.water.room.InventoryEntity
import com.lhr.water.room.SqlDatabase
import com.lhr.water.util.manager.jsonObjectToJsonString
import com.lhr.water.util.manager.jsonStringToJson
import org.json.JSONObject

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
            var jsonObject = jsonStringToJson(inventoryEntity.formContent)
            binding.textMaterialName.text = jsonObject.getString("materialName")
            binding.textMaterialSpec.text = jsonObject.getString("materialSpec")
            binding.textMaterialUnit.text = jsonObject.getString("materialUnit")
            binding.textQuantity.text = Editable.Factory.getInstance().newEditable(jsonObject.getString("actualQuantity"))

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
                jsonObject.put("actualQuantity", binding.textQuantity.text)

                var tempInventoryEntity = InventoryEntity()
                tempInventoryEntity.formNumber = inventoryEntity.formNumber
                tempInventoryEntity.formContent = jsonObjectToJsonString(jsonObject)
                SqlDatabase.getInstance().getInventoryDao().insertOrUpdate(tempInventoryEntity)
                FormRepository.getInstance(context).loadInventoryForm()
            }
        }
    }

    interface Listener {
        fun onItemClick(item: JSONObject)
    }
}