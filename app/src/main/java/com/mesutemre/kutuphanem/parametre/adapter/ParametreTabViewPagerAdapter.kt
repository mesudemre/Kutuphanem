package com.mesutemre.kutuphanem.parametre.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ParametreTabViewPagerAdapter(fragmentManager:FragmentManager,lifeCycle:Lifecycle,var fragmentList:ArrayList<Fragment>)
    : FragmentStateAdapter(fragmentManager,lifeCycle) {

    override fun getItemCount(): Int {
        return fragmentList.size;
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList.get(position);
    }
}