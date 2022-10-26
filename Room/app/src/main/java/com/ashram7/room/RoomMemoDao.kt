package com.ashram7.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RoomMemoDao {
    @Query("select * from room_memo")
    fun getAll(): List<RoomMemo>

    // REPLACE를 import 할 때 androidx.room 패키지로 시작하는 것을 선택
    @Insert(onConflict = REPLACE) // 동일한 키를 가진 값이 입력되었을 때 UPDATE 쿼리로 실행하라는 옵션
    fun insert(memo: RoomMemo)

    @Delete
    fun delete(memo: RoomMemo)
}