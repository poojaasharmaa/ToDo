package com.example.todo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo.model.ToDo

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
      fun insertAll(list: List<ToDo>)

    @Query("select DISTINCT userId From todo ")
      fun getUsersList():List<Int>

    @Query("select * From todo where userId=:userId")
      fun getDataByUserId(userId:Int):List<ToDo>

    @Query("UPDATE todo SET completed=:completed where id=:id")
    fun updateStatusByUserId(id:Int,completed:Boolean)


}