package com.ashram7.newworkhttpurlconnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ashram7.newworkhttpurlconnection.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    // 바인딩을 생성한 후 binding 프로퍼티에 저장
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonRequest.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // 주소 입력 필드에 주소를 가져와 https로 시작하지 않으면 앞에 http://를 붙여준다.
                    // http를 시용하려면 AndroidManifest.xml 파일에 부가적인 설정이 필요함.
                    var urlText = binding.editUrl.text.toString()
                    if (!urlText.startsWith("https")) {
                        urlText = "https://${urlText}"
                    }
                    // 주소를 URL 객체로 반환하고 변수에 저장한다.
                    // 여기서 URL은 java.net를 import한다.
                    val url = URL(urlText)
                    /* URL 객체에서 openConnection() 메서드를 사용해서 서버와의 연결을 생성한다.
                       그리고 HttpURLConnection으로 형변환해준다. openConnection() 메서드에서 반환되는 값은
                       URLConnection이라는 추상(설계) 클래스이다. 추상 클래스를 사용하기 위해서는 실제 구현 클래스인
                       HttpURLConnection으로 변환하는 과정이 필요하다. */
                    val urlConnection = url.openConnection() as HttpURLConnection
                    // 연결된 커넥션에 요청방식을 설정한다.
                    urlConnection.requestMethod = "GET"
                    // 응답이 정상이면 응답 데이터를 처리한다.
                    if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                        // 입력스트림(데이터를 읽어오는 스트림)을 연결하고 버퍼에 담아서 데이터를 읽을 준비
                        val streamReader = InputStreamReader(urlConnection.inputStream)
                        val buffered = BufferedReader(streamReader)

                        // 반복문을 돌면서 한 줄씩 읽은 데이터를 content 변수에 저장
                        val content = StringBuilder()
                        while (true) {
                            val line = buffered.readLine() ?: break
                            content.append(line)
                        }
                        // 사용한 스트림과 커넥션을 모두 해제
                        buffered.close()
                        urlConnection.disconnect()
                        // 화면의 텍스트뷰에 content 변수에 저장된 값을 입력
                        launch(Dispatchers.Main) {
                            binding.textContent.text = content.toString()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}