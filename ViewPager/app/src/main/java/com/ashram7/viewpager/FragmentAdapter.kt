package com.ashram7.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    var fragmentList = listOf<Fragment>()

    //어댑터가 화면에 보여줄 전체 프래그먼트의 개수를 반환
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    //position에 해당하는 위치의 프래그먼트를 만들어서 안드로이드에 반환
    override fun createFragment(position: Int): Fragment {
        return fragmentList.get(position)
    }
}