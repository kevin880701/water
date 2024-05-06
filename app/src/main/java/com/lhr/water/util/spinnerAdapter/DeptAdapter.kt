package com.lhr.water.util.spinnerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.lhr.water.databinding.ItemRegionSpinnerBinding
import com.lhr.water.room.RegionEntity

class DeptAdapter(private val context: Context, private val data: ArrayList<RegionEntity>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemRegionSpinnerBinding
        val view: View

        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            binding = ItemRegionSpinnerBinding.inflate(inflater, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemRegionSpinnerBinding
        }

        val currentItem = data[position]
        binding.textView.text = "${currentItem.deptName}-${currentItem.mapSeq}"

        return view
    }
}