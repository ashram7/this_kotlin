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
    val binding by lazy { ActivityChatListBinding.inflate(layoutInflater)}
    val database = Firebase.database("https://this-is-android-with-kot-d7f5e-default-rtdb.asia-southeast1.firebasedatabase.app/")

    val roomsRef  = database.getReference("rooms")
    companion object {
        var userId: String = ""
        var userName: String = ""
    }

    // 5)번 방 목록을 저장할 roomList와 어뎁터를 저장할 adapter 프로퍼티를 선언
    val roomList = mutableListOf<Room>() // 파이어베이스에서 데이터를 불러온 후 저장할 변수
    lateinit var adapter: ChatRoomListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId") ?: "none"
        userName = intent.getStringExtra("userName") ?: "Anonymous"

        with(binding) {
            btnCreate.setOnClickListener { openCreateRoom() }
        }
        // 6)번 아답터와 뷰 연결
        adapter = ChatRoomListAdapter(roomList)
        with(binding) {
            recyclerRooms.adapter = adapter
            recyclerRooms.layoutManager = LinearLayoutManager(baseContext)
        }
        // 8)번 파이어베이스 연결
        loadRooms()
    }

    /* 7)번 파이어베이스 리얼타임 데이터베이스에서 rooms 목록을 불러온 후 roomList에 저장
    adapter.notifiyDataSetChanged() 메서드를 호출해서 목록을 갱신한다. */
    fun loadRooms() {
        roomsRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 방 목록 삭제
                roomList.clear()
                for(item in snapshot.children) {
                    item.getValue(Room::class.java)?.let { room ->
                        roomList.add(room) // 방 목록에 추가
                    }
                }
                // 아답터 갱신
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                print(error.message)
            }
        })
    }

    fun openCreateRoom() {
        val editTitle = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("방 이름")
            .setView(editTitle)
            .setPositiveButton("만들기") { dlg, id ->
                createRoom(editTitle.text.toString())
            }
        dialog.show()
    }

    fun createRoom(title:String) {
        val room = Room(title, userName)
        val roomId = roomsRef.push().key!!
        room.id = roomId
        roomsRef.child(roomId).setValue(room)
    }
}


class ChatRoomListAdapter(val roomList:MutableList<Room>)
    : RecyclerView.Adapter<ChatRoomListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val room = roomList.get(position)
        holder.setRoom(room)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // 10)번 목록의 아이템을 클릭하면 채팅방으로 이동하는 코드를 추가
        lateinit var mRoom:Room
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatRoomActivity::class.java)
                intent.putExtra("roomId", mRoom.id)
                intent.putExtra("roomTitle", mRoom.title)

                itemView.context.startActivity(intent)
            }
        }

        fun setRoom(room:Room) {
            // 11)번 클릭리스너를 init에서 구현하기 때문에 setRoom 메서드에서 채팅방을 저장한다.
            this.mRoom = room
            itemView.findViewById<TextView>(android.R.id.text1).setText(room.title)
        }
    }
}
