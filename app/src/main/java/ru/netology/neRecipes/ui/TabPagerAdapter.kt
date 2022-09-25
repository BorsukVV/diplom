package ru.netology.neRecipes.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabPagerAdapter(fm: FragmentActivity, private val list: List<Fragment>) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

}