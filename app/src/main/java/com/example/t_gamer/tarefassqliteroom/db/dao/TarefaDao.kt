package com.example.t_gamer.tarefassqliteroom.db.dao

import androidx.room.*
import com.example.t_gamer.tarefassqliteroom.entidades.Tarefa

@Dao
interface TarefaDao {
    @Query("SELECT * FROM tarefa ORDER BY titulo, descricao")
    fun getAll(): List<Tarefa>

    @Query("SELECT * FROM tarefa WHERE id = :id LIMIT 1")
    fun findById(id: Int): Tarefa?

    @Query("SELECT * FROM tarefa WHERE titulo LIKE :titulo AND descricao LIKE :descricao")
    fun findByName(titulo: String, descricao: String): List<Tarefa>

    @Insert
    fun insert(tarefa: Tarefa)

    @Update
    fun update(tarefa: Tarefa)

    @Delete
    fun remove(tarefa: Tarefa)
}