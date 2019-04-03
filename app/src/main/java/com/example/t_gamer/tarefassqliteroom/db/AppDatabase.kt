package com.example.t_gamer.tarefassqliteroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.t_gamer.tarefassqliteroom.db.dao.TarefaDao
import com.example.t_gamer.tarefassqliteroom.entidades.Tarefa

@Database(entities = arrayOf(Tarefa::class), version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tarefaDao(): TarefaDao
}