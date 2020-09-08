package com.example.fcmandstoreprep.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fcmandstoreprep.MainActivity
import com.example.fcmandstoreprep.R
import com.example.fcmandstoreprep.adapters.AllNotesAdapter
import com.example.fcmandstoreprep.databinding.FragmentNotesBinding
import com.example.fcmandstoreprep.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FragmentNotes: Fragment(R.layout.fragment_notes) {

    private lateinit var binding: FragmentNotesBinding
    private var allNotesList = mutableListOf<Note>()
    private val mAdapter = AllNotesAdapter(allNotesList)
    private val notesCollection = FirebaseFirestore.getInstance().collection("Notes")

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "All notes"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
            setHasFixedSize(true)
        }

        showAllNotes()


        binding.goToAddFragmentBtn.setOnClickListener {
            if(FirebaseAuth.getInstance().currentUser == null){
                view.findNavController().navigate(R.id.action_fragmentNotes_to_fragmentMe)
            }
            else{
                view.findNavController().navigate(R.id.action_fragmentNotes_to_fragmentAdd)
            }
        }

    }

    private fun showAllNotes(){
        notesCollection.orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                for(result in task.result!!){
                    val note = result.toObject(Note::class.java)
                    allNotesList.add(note)
                }
                mAdapter.notifyDataSetChanged()
            }
        }
    }

}