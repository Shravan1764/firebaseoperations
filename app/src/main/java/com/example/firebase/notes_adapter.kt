package com.example.firebase

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.NotesBinding

class notes_adapter(private val notes:List<Dataitems>,private val itemclicklistener:OnItemClickListener) : RecyclerView.Adapter<notes_adapter.ViewHolder>() {

    interface OnItemClickListener{
        fun ondeleteClick(noteid : String)
        fun onupdateClick(noteid : String,title : String,description : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notes_adapter.ViewHolder {
        val binding : NotesBinding = NotesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: notes_adapter.ViewHolder, position: Int) {

        val note = notes[position]
        holder.bind(note)
        holder.binding.update.setOnClickListener {
           itemclicklistener.onupdateClick(note.noteid,note.title,note.description)
        }
        holder.binding.delete.setOnClickListener {
            itemclicklistener.ondeleteClick(note.noteid)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class ViewHolder(val binding: NotesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(dataitems: Dataitems) {
            binding.texttitle.text = dataitems.title
            binding.textdescription.text = dataitems.description
            binding.noteid.text = dataitems.noteid

        }

    }
}


