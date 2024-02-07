package com.example.todolistapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {

    //get all
    @Query("SELECT * FROM TodoEntity ORDER BY importance") //중요도 순으로 정렬 
    fun getAllTodo():List<TodoEntity>

    //insert todo
    @Insert
    fun insertTodo(todo: TodoEntity)

    //delete todo
    @Delete
    fun deleteTodo(todo: TodoEntity)
}