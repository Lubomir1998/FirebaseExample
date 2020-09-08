package com.example.fcmandstoreprep.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fcmandstoreprep.databinding.NoteItemBinding
import com.example.fcmandstoreprep.model.Note
import java.text.SimpleDateFormat

class MyNotesAdapter(var listOfNotes: MutableList<Note>): RecyclerView.Adapter<MyNotesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNote = listOfNotes[position]

        holder.titleTextView.text = currentNote.title
        holder.descriptionTextView.text = currentNote.description

        val simpleDateFormat = SimpleDateFormat("MMM dd, yyyy, HH:mm")
        val formattedDate = simpleDateFormat.format(currentNote.timestamp!!)

        holder.dateTextView.text = formattedDate

    }

    override fun getItemCount(): Int = listOfNotes.size



    class MyViewHolder(itemView: NoteItemBinding): RecyclerView.ViewHolder(itemView.root){
        val titleTextView = itemView.titleTextView
        val descriptionTextView = itemView.descriptionTextView
        val dateTextView = itemView.dateTextView

    }


}