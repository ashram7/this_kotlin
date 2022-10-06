package com.ashram7.controlflowfor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 1. 일반적인 반복문 사용으로 10번 반복하기
        /*for(index in 1..10){
            Log.d("For", "현재 숫자는 ${index}")
        }*/
        var array = arrayOf("JAN","FEB","MAR","APR","MAY","JUN")
        /*for ( index in 0 until array.size ) {
            Log.d("For", "현재 월은 ${array.get(index)} 입니다")
        }*/
        /*for(index in 0..10 step 3){
            Log.d("For", "건너뛰기 : ${index}")
        }*/
        /*for(index in 10 downTo 0) {
            Log.d("For", "감소시키기 : ${index}")
        }*/
        /*for(index in 10 downTo 0 step 3) {
            Log.d("For", "건너뀌면서 감소시키기 : ${index}")
        }*/
        for(month in array) {
            Log.d("For", "현재 월은 ${month} 입니다")
        }
    }
}


