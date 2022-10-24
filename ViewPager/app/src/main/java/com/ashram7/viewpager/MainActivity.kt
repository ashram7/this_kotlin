package com.ashram7.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ashram7.viewpager.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //프래그먼트 목록을 생성
        val fragmentList = listOf(FragmentA(), FragmentB(), FragmentC(), FragmentD())
        //어댑터를 생성하고 앞에서 생성해둔 프래그먼트 목록을 저장
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        //레이아웃의 viewPager를 import하고 어댑터를 적용
        binding.viewPager.adapter = adapter

        //메뉴명으로 사용할 이름들을 배열에 저장
        val tabTitles = listOf("A", "B", "C", "D")
        //TabLayoutMediator를 사용해서 TabLayout과 뷰페이지를 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            //tab 파라미터의 text 속성에 앞에서 미리 정의해둔 메뉴명을 입력
            tab.text = tabTitles[position]
        }.attach()
    }
}











