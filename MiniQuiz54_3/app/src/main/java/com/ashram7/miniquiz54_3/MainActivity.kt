package com.ashram7.miniquiz54_3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var layout = findViewById<LinearLayout>(R.id.linearLayout)
        layout.addView(CustomView(this))
    }
}

class CustomView(context: Context) : View(context) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 페인트 변수 생성 및 빨간색 테두리 설정
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        paint.color = Color.RED

        // 빨간색 테두리 사각형 생성
        val rectf = RectF(50f, 300f, 400f, 450f)
        canvas?.drawRoundRect(rectf, 50f, 100f, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.BLUE

        // 파란색 배경의 사각형 생성
        val rectf2 = RectF(50f, 300f, 400f, 450f)
        canvas?.drawRoundRect(rectf2, 50f, 100f, paint)


        // 빨간색으로 채운 반지름 100f인 원 생성
        paint.color = Color.RED
        canvas?.drawCircle(500f, 500f, 100f, paint)

        // 하얀색으로 채운 반지름 50f인 원 생성
        paint.color = Color.WHITE
        canvas?.drawCircle(500f, 500f, 50f, paint)
    }
}