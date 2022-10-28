package com.ashram7.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashram7.room.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    // 수정을 위해서 MainActivity 연결
    var mainActivity:MainActivity? = null
    var helper:RoomHelper? = null
    var listData = mutableListOf<RoomMemo>()

    //Ctrl + I를 클릭
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val RoomMemo = listData.get(position)
        holder.setRoomMemo(RoomMemo)
    }

    //위치는 함수의 맨 바깥쪽에 따로 작성
    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var mRoomMemo:RoomMemo? = null
        init {
            binding.buttonDelete.setOnClickListener {
                helper?.roomMemoDao()?.delete(mRoomMemo!!)
                listData.remove(mRoomMemo)
                notifyDataSetChanged()
            }

            // 수정 기능 추가
            binding.textContent.setOnClickListener {
                mainActivity?.setUpdate(mRoomMemo!!)
            }
        }
        fun setRoomMemo(RoomMemo:RoomMemo) {
            binding.textNo.text = "${RoomMemo.no}"
            binding.textContent.text = RoomMemo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            // 날짜 포맷은 SimpleDateFormat으로 설정합니다.
            binding.textDatetime.text = "${sdf.format(RoomMemo.datetime)}"
            this.mRoomMemo = RoomMemo
        }
    }
}

