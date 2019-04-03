package com.example.t_gamer.tarefassqliteroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.room.Room
import com.example.t_gamer.tarefassqliteroom.db.AppDatabase
import com.example.t_gamer.tarefassqliteroom.db.dao.TarefaDao
import com.example.t_gamer.tarefassqliteroom.entidades.Tarefa
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var tarefaDao: TarefaDao
    lateinit var adapter: ArrayAdapter<Tarefa>
    var tarefaEditing: Tarefa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "tarefa.db"
            )
                .allowMainThreadQueries()
                .build()
        tarefaDao = db.tarefaDao()

        btSave.setOnClickListener { saveTarefa() }

        listTarefa.setOnItemClickListener { _, _, position, _ ->

            editTarefa(getTarefaFromList(position))
            btDelete.setOnClickListener{removeTarefa(getTarefaFromList(position))}
        }

        listTarefa.setOnItemLongClickListener { _, _, position, _ ->
            doneTask(getTarefaFromList(position))
            true
        }

        btDelete.visibility = View.INVISIBLE

        loadData()
    }

    private fun removeTarefa(tarefa: Tarefa) {
        tarefaDao.remove(tarefa)
        loadData()
    }

    private fun getTarefaFromList(position: Int) =
        adapter.getItem(position) as Tarefa

    private fun editTarefa(tarefa: Tarefa) {
        btDelete.visibility = View.VISIBLE

        txtTitulo.setText(tarefa.titulo)
        txtDescricao.setText(tarefa.descricao)

        txtTitulo.requestFocus()

        tarefaEditing = tarefa
    }

    private fun saveTarefa() {
        val titulo = txtTitulo.text.toString()
        val descricao = txtDescricao.text.toString()
        val resultado = "-"

        if (tarefaEditing != null) {
            tarefaEditing?.let { tarefa ->
                tarefa.titulo = titulo
                tarefa .descricao = descricao
                tarefaDao.update(tarefa)
            }
        } else {
            val tarefa = Tarefa(titulo, descricao, resultado)
            tarefaDao.insert(tarefa)
        }

        loadData()
    }

    private fun loadData() {
        val tarefa = tarefaDao.getAll()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            tarefa
        )
        listTarefa.adapter = adapter

        clear()
    }

    /* Alterar status para tarefa feita */
    private fun doneTask(tarefa: Tarefa) {
        tarefa?.let { tarefa ->
            tarefa.resultado = "Feita"
            tarefaDao.update(tarefa)
        }

        loadData()
    }

    private fun clear() {
        txtTitulo.setText("")
        txtDescricao.setText("")

        txtTitulo.requestFocus()

        tarefaEditing = null

        btDelete.visibility = View.INVISIBLE
    }
}
