package com.example.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todo: TodoEntity): Long

    @Update
    suspend fun update(todo: TodoEntity)

    @Delete
    suspend fun delete(todo: TodoEntity)

    @Query("SELECT * FROM todos WHERE id = :todoId LIMIT 1")
    suspend fun getTodoById(todoId: Long): TodoEntity?

    @Query("SELECT * FROM todos WHERE userId = :userId ORDER BY createdAt DESC")
    fun getTodosByUserId(userId: Long): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE userId = :userId AND isCompleted = 0 ORDER BY createdAt DESC")
    fun getActiveTodosByUserId(userId: Long): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE userId = :userId AND isCompleted = 1 ORDER BY createdAt DESC")
    fun getCompletedTodosByUserId(userId: Long): Flow<List<TodoEntity>>

    @Query("DELETE FROM todos WHERE id = :todoId")
    suspend fun deleteById(todoId: Long)
}
