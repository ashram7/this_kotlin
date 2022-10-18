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

        binding.checkApple.setOnCheckedChangeListener { buttonView, isChecked ->
            apple = isChecked
            printCheckedItems()
        }
        binding.checkBanana.setOnCheckedChangeListener { buttonView, isChecked ->
            banana = isChecked
            printCheckedItems()
        }
        binding.checkOrange.setOnCheckedChangeListener { buttonView, isChecked ->
            orange = isChecked
            printCheckedItems()
        }
    }

    fun printCheckedItems(){
        var result = ""
        if (apple) result = " 사과"

        if (banana) {
            if (result.isNotEmpty()) result += "와"
            result += " 바나나"
        }
        if (orange) {
            if (result.isNotEmpty()) result += "와"
            result += " 오렌지"
        }
        if (result.isNotEmpty()) {
            binding.textView.text = "${result}가 선택되었습니다."
        } else {
            binding.textView.text = "선택되지 않았습니다."
        }
    }
}