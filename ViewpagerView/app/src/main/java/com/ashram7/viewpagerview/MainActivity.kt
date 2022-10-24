package com.ashram7.viewpagerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ashram7.viewpagerview.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //뷰페이저에서 사용할 데이터를 가상으로 생성한 후 textList 변수(컨텐츠 보여지는 내용)에 담는다.
        val textList = listOf("뷰A", "뷰B", "뷰C", "뷰D")
        //커스텀어댑터를 생성
        val customAdapter = CustomPagerAdapter()
        //생성해둔 가상 데이터(컨텐츠 보여지는 내용)를 어댑터에 전달
        customAdapter.textList = textList
        //viewPager에 어댑터를 연결
        binding.viewPager.adapter = customAdapter
        //메뉴명으로 사용할 이름들을 배열에 저장
        val tabTitles = listOf("View A", "View B", "View C", "View D")
        //TabLayoutMediator를 사용해서 탭 레이아웃과 뷰페이지를 연결
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}