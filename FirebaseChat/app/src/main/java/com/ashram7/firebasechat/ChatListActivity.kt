package com.ashram7.firebasechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashram7.firebasechat.databinding.ActivityChatListBinding
import com.ashram7.firebasechat.model.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatListActivity : AppCompatActivity() {
    // 다음과 같이 binding를 생성한다.
    val binding by lazy { ActivityChatListBinding.inflate(layoutInflater)}
    val database = Firebase.database("https://this-is-android-with-kot-942ef-default-rtdb.asia-southeast1.firebasedatabase.app/")

    // database와 rooms 노드를 연결한다.
    val roomsRef  = database.getReference("rooms")

    // 로그인한 사용자 정보를 다른 액티비티에서 사용할 수 있도록 companion object 로 생성
    companion object {
        var userId: String = ""
        var userName: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 인텐트로 넘어온 사용자 정보를 저장한다.
        userId = intent.getStringExtra("userId") ?: "none"
        userName = intent.getStringExtra("userName") ?: "Anonymous"

        // 방 만들기 버튼 클릭 시 openCreateRoom() 메서드를 호출하는 코드를 추가
        with(binding) {
            btnCreate.setOnClickListener { openCreateRoom() }
        }
    }

    // 방 만들기 버튼을 클릭했을 때 호출하는 openCreateRoom() 메서드를 생성한다.
    fun openCreateRoom() {
        // 1. 방 이름을 입력할 EditText를 코드로 생성
        val editTitle = EditText(this)
        // 2. 다이얼로그 생성
        val dialog = AlertDialog.Builder(this)
            .setTitle("방 이름")
            .setView(editTitle) // 방이름을 입력할 EditText를 여기에 넣는다
            .setPositiveButton("만들기") { dlg, id ->
                // if 방이름 입력 여부 체크 필요 createRoom은 아직 구현전 (바로 아래서 구현함)
                createRoom(editTitle.text.toString())
                // 만들기 버튼 클릭 -> 방이름 createRoom() 메서드에 전달
            }
        // 3. 다이얼로그 표시
        dialog.show()
    }

    fun createRoom(title:String) {
        // 방 데이터 생성
        val room = Room(title, userName)
        // 방 아이디를 만들어서 입력 push().key를 사용한 것은 앞에서도 한 번 사용했음
        val roomId = roomsRef.push().key!!
        room.id = roomId
        // 파이어베이스에 전송
        roomsRef.child(roomId).setValue(room)
    }
}