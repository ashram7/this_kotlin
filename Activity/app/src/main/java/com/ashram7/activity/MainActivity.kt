package com.ashram7.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.ashram7.activity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // SubActivity 에서 돌려준 값을 받는 코드
        val activityResult = registerForActivityResult(
            ActivityResultContracts
            .StartActivityForResult()){
            // resultCode 코드가 정상인지 체크하는 코드
            if (it.resultCode == RESULT_OK){
                // 돌려받은 인텐트에서 메세지를 꺼내 변수를 저장
                val message = it.data?.getStringExtra("returnValue")
                // 해당하는 메세지를 화면에 보여주는 코드
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

        val intent = Intent(this, SubActivity::class.java)
        intent.putExtra("from1", "Hello Bundle")
        intent.putExtra("from2", 2022)
        binding.btnStart.setOnClickListener {activityResult.launch(intent)}
    }
}