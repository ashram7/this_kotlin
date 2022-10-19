package com.ashram7.containerrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashram7.containerrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val data: MutableList<Memo> = loadData()
        var adapter = CustomAdapter()
        adapter.listData = data
        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // 100개의 list를 가상으로 만듭니다.
    fun loadData(): MutableList<Memo>{
        //메서드 안에 리턴할 MutableList 컬렉션을 선언
        var data: MutableList<Memo> = mutableListOf()
        for (no in 1..100){
            var title = "이것이 안드로이드다 ${no}"
            var date = System.currentTimeMillis()
            var memo = Memo(no, title, date)
            data.add(memo)
        }
        return data
    }
}