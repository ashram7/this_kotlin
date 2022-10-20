package com.ashram7.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment()
    }

    fun setFragment() {
        //ListFragment를 생성
        val listFragment: ListFragment = ListFragment()
        // 프래그먼트 매니저를 통해서 트랜잭션을 시작하고, 시작한 트랜잭션을 변수에 저장
        val transaction = supportFragmentManager.beginTransaction()
        // 트랜잭션의 add() 메서드로 frameLayout을 id로 가지고 있는 레이아웃에 앞에서 생성한 listFragment 삽입
        transaction.add(R.id.frameLayout, listFragment)
        // setFragment() 메서드 안에 다음과 같이 ListFragment를 생성
        transaction.commit()
    }

    fun goDetail() {
        val detailFragment = DetailFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, detailFragment)
        transaction.addToBackStack("detail")
        transaction.commit()
    }

    fun goBack(){
        onBackPressed()
    }
}











