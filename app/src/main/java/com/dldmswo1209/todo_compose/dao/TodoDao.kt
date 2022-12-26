package com.dldmswo1209.todo_compose.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dldmswo1209.todo_compose.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun getAllTodo() : LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)
}