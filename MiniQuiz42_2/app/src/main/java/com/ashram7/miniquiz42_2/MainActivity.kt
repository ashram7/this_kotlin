package com.ashram7.miniquiz42_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ashram7.miniquiz42_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var apple = false
    var banana = false
    var orange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }

    fun printCheckedItems(){

    }
}