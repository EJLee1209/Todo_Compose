package com.dldmswo1209.todo_compose.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dldmswo1209.todo_compose.dao.TodoDao
import com.dldmswo1209.todo_compose.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
    companion object{
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(
            context: Context
        ) : TodoDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}