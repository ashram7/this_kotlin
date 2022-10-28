package com.ashram7.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface RoomMemoDao {
    @Query("select * from room_memo")
    fun getAll(): List<RoomMemo>

    @Insert(onConflict = REPLACE) // 동일한 키를 가진 값이 입력되었을 때 UPDATE 쿼리로 실행하라는 옵션
    fun insert(memo: RoomMemo)

    @Delete
    fun delete(memo: RoomMemo)

    @Update
    fun update(memo:RoomMemo)
}