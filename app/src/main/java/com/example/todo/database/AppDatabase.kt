package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.model.ToDo

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun toDoDao():Dao
    companion object {
        fun getAppDatabase(context: Context): AppDatabase? {
            var INSTANCE: AppDatabase? = null
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ToDoDatabase.db").build()
            }
            return INSTANCE
        }

         fun getMainThread(context: Context): AppDatabase? {
            var mainInstance: AppDatabase? = null
            if (mainInstance == null) {
                mainInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ToDoDatabase.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return mainInstance
        }
    }
}