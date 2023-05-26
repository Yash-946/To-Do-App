package com.example.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRVAdapter(private val context:Context,
                    private val listener:INotesRVadapter):
    RecyclerView.Adapter<NoteRVAdapter.NoteViewHolder>() {

    private val allnotes = ArrayList<Note>()

    inner class NoteViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val textview = itemview.findViewById<TextView>(R.id.text)
        val delebutton = itemview.findViewById<ImageView>(R.id.deletebutton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewholder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note,parent,false))
        viewholder.delebutton.setOnClickListener{
            listener.onItemClicked(allnotes[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun getItemCount(): Int {
        return allnotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allnotes[position]
        holder.textview.text = currentNote.text
    }

    fun updateList(newList:List<Note>){
        allnotes.clear()
        allnotes.addAll(newList)

        notifyDataSetChanged()
    }
}
interface INotesRVadapter{
    fun onItemClicked(note:Note)
}