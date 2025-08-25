package com.example.mytestnav.mybook

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter(
    list: List<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle)
:FragmentStateAdapter(fm,lifecycle) {
    val framentlist = list
    override fun getItemCount(): Int {
        return framentlist.size
    }

    override fun createFragment(position: Int): Fragment {
        return framentlist[position]
    }
}