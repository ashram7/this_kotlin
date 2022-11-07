package com.ashram7.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ashram7.firebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val database = Firebase.database("https://this-is-android-with-kot-d7f5e-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef = database.getReference("users")

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fun addItem(user:User) {
            val id = myRef.push().key!!
            user.id = id
            myRef.child(id).setValue(user)
        }

        with(binding){
            btnPost.setOnClickListener {
                val name = editName.text.toString()
                val password = editPassword.text.toString()
                val user = User(name, password)
                addItem(user)
            }
        }

        myRef.addValueEventListener( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.textList.setText("")
                for (item in snapshot.children) {
                    item.getValue(User::class.java)?.let { user ->
                        binding.textList.append("${user.name} : ${user.password} \n")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}

class User {
    var id:String = ""
    var name:String = ""
    var password:String = ""

    constructor() // 파이어베이스에서 데이터 변환을 위해서 필요

    constructor(name:String, password:String) {
        this.name = name
        this.password = password
    }
}