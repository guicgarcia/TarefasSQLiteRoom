package com.example.t_gamer.tarefassqliteroom.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefa")
data class Tarefa(
    @ColumnInfo(name = "titulo")
    var titulo: String,
    @ColumnInfo(name = "descricao")
    var descricao: String,
    var resultado: String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    val fullText get() = "${titulo} ${descricao} ${resultado} "

    override fun toString()= fullText
}