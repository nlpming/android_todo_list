package com.example.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.dao.TodoDao
import com.example.myapplication.data.local.dao.UserDao
import com.example.myapplication.data.local.entity.TodoEntity
import com.example.myapplication.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, TodoEntity::class],
    version = 1,
    exportSchema = true
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun todoDao(): TodoDao
}
