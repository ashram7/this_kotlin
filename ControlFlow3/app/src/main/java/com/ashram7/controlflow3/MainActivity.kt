package com.ashram7.controlflow3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var eraOfRyu = 2.72
        var eraOfDegrom = 2.43

        var era = if (eraOfRyu < eraOfDegrom) {
            Log.d("MLB_Result", "2022 류현진이 디그롬을 이겼습니다.")
            eraOfRyu
        } else {
            Log.d("MLB_Result", "2022 디그롬이 류현진을 이겼습니다.")
            eraOfDegrom
        }

        Log.d("MLB_Result", "2022 MLB에서 가장 높은 ERA는 ${era}입니다.")
    }
}