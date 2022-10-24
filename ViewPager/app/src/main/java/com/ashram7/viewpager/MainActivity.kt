package com.ashram7.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ashram7.viewpager.databinding.ActivityMainBinding

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
    }
}