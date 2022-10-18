package com.ashram7.miniquiz42_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    lateinit var textview_msg : TextView
    lateinit var pB : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textview_msg = findViewById(R.id.numText)
        var second = 0
        timer(period = 1000){
            second++
            runOnUiThread {
                textview_msg.text = second.toString()
            }
        }
    }
}