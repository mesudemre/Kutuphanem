package com.mesutemre.kutuphanem.anasayfa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mesutemre.kutuphanem.anasayfa.ui.TanitimFragment

class TanitimTabViewPagerAdapter(fragmentActivity:FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3;
    }

    override fun createFragment(position: Int): Fragment {
        return TanitimFragment.newInstance(position);
    }
}