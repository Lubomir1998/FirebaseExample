package com.example.fcmandstoreprep.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fcmandstoreprep.databinding.NotesItem2Binding
import com.example.fcmandstoreprep.model.Note
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat

class AllNotesAdapter(private var listOfAllNotes: MutableList<Note>): RecyclerView.Adapter<AllNotesAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = NotesItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat", "ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = listOfAllNotes[position]

        if(FirebaseAuth.getInstance().currentUser != null && current.userName == FirebaseAuth.getInstance().currentUser?.displayName){
            holder.userNameTextView.setTextColor(Color.parseColor("#159DF3"))
        }
        holder.userNameTextView.text = current.userName
        holder.titleTextView.text = current.title
        holder.descriptionTextView.text = current.description

        val simpleDateFormat = SimpleDateFormat("MMM dd, yyyy, HH:mm")
        val formattedDate = simpleDateFormat.format(current.timestamp!!)

        holder.dateTextView.text = formattedDate

    }

    override fun getItemCount(): Int = listOfAllNotes.size

    class MyViewHolder(itemView: NotesItem2Binding): RecyclerView.ViewHolder(itemView.root){
        val titleTextView = itemView.titleTextView
        val descriptionTextView = itemView.descriptionTextView
        val dateTextView = itemView.dateTextView
        val userNameTextView = itemView.nameTextView

    }

}