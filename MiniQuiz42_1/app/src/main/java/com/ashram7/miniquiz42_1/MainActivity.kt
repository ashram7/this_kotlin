package com.ashram7.miniquiz42_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var txtView : TextView
    lateinit var rdGroup : RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtView = findViewById(R.id.txtView)
        rdGroup = findViewById(R.id.rdGroup)

        rdGroup.setOnCheckedChangeListener{radioGroup, checkedId ->
            when(checkedId) {
                R.id.rdApple->{
                    txtView.text = "사과"
                }
                R.id.rdBanana->{
                    txtView.text = "바나나"
                }
                R.id.rdOrange->{
                    txtView.text = "오렌지"
                }
            }
        }
    }
}








