package com.dldmswo1209.todo_compose.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dldmswo1209.todo_compose.db.TodoDatabase
import com.dldmswo1209.todo_compose.model.Todo
import com.dldmswo1209.todo_compose.repo.TodoRepo

class MainViewModel(application: Application) : ViewModel() {
    val allTodo: LiveData<List<Todo>>
    private val repository : TodoRepo

    init{
        val todoDb = TodoDatabase.getDatabase(application)
        val todoDao = todoDb.todoDao()
        repository = TodoRepo(todoDao)

        allTodo = repository.allTodo
    }

    fun insertTodo(todo: Todo){
        repository.insertTodo(todo)
    }

    fun deleteTodo(todo: Todo){
        repository.deleteTodo(todo)
    }
}