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

    @Insert(onConflict = REPLACE) // 동일한 키를 가진 값이 입력되었을 때 UPDATE 쿼리로 실행하라는 옵션
    fun insert(memo: RoomMemo)

    @Delete
    fun delete(memo: RoomMemo)
}