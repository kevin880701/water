package com.lhr.water.util.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, fragments: ArrayList<Fragment>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    var fragments = fragments

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}