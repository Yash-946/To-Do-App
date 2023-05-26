package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVadapter {

    private lateinit var viewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var button: Button
    private lateinit var input:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.save)
        input = findViewById(R.id.input)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val adapter = NoteRVAdapter(this,this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allnotes.observe(this, Observer {list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        button.setOnClickListener{
            val notetext = input.text.toString()
            if (notetext.isNotEmpty()){
                viewModel.insertNote(Note(notetext))
                Toast.makeText(this,"$notetext Inserted",Toast.LENGTH_LONG).show()
                input.text.clear()
            }
        }
    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.text} Deleted",Toast.LENGTH_LONG).show()
    }
}