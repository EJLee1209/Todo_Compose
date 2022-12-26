package com.dldmswo1209.todo_compose.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dldmswo1209.todo_compose.dao.TodoDao
import com.dldmswo1209.todo_compose.model.Todo
import kotlinx.coroutines.*

class TodoRepo(private val todoDao: TodoDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val allTodo : LiveData<List<Todo>> = todoDao.getAllTodo()

    fun insertTodo(todo: Todo){
        coroutineScope.launch(Dispatchers.IO) {
            todoDao.insertTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo){
        coroutineScope.launch(Dispatchers.IO) {
            async {
                delay(500)
            }.await()
            todoDao.deleteTodo(todo)
        }
    }


}