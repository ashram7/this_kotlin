package com.ashram7.firebasestorage

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.ashram7.firebasestorage.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    // 버킷에 연결하는 코드를 추가
    val storage = Firebase.storage("gs://this-is-android-with-kot-d7f5e.appspot.com")
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // 3. 스토리지 업로드 메서드 호출
        //uploadImage(uri)
        if (uri != null) {
            uploadImage(uri)
        }
    }

    fun uploadImage(uri: Uri) {
        // 1. 경로 + 사용자ID + 밀리초로 파일주소 만들기
        val fullPath   = makeFilePath("images", "temp",uri)
        // 2. 스토리지에 저장할 경로 설정 - images/파일명
        val imageRef   = storage.getReference(fullPath)
        // 3. 업로드 태스크 생성
        val uploadTask = imageRef.putFile(uri)

        // 4. 업로드 실행 및 결과 확인
        uploadTask.addOnFailureListener {
            Log.d("스토리지", "실패=>${it.message}")
            /* 작업이 성공하면 해당 경로를 저장해 두었다가 이미지를 불러올때 사용
            여기서는 Logcat에 출력된 주소를 복사해서 사용 */
        }.addOnSuccessListener { taskSnapshot ->
            Log.d("스토리지", "성공 주소=>${fullPath}")
        }
    }

    fun makeFilePath(path:String, userId:String, uri:Uri): String {
        // 마임타입 예) images/jpeg
        val mimeType = contentResolver.getType(uri)?:"/none"
        // 확장자  예) jpeg
        val ext = mimeType.split("/")[1]
        // 시간값   예) 1232131241312
        val timeSuffix = System.currentTimeMillis()
        // 완성
        val filename = "${path}/${userId}_${timeSuffix}.${ext}"
        // 예) 경로/사용자ID_1232131241312.jpeg
        return filename
    }

}