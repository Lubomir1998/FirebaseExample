package com.example.fcmandstoreprep.ui

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query


class FragmentNotes: Fragment(R.layout.fragment_notes) {

    private lateinit var binding: FragmentNotesBinding
    private var allNotesList = mutableListOf<Note>()
    private val mAdapter = AllNotesAdapter(allNotesList)
    private val notesCollection = FirebaseFirestore.getInstance().collection("Notes")
    private lateinit var noteListener: ListenerRegistration
    private val TAG = "Tag"

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

    override fun onStart() {
        super.onStart()
        noteListener = notesCollection.orderBy("timestamp", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null) {
                return@addSnapshotListener
            }
            if(value != null){

                // we clear the list to avoid duplicate data
                allNotesList.clear()

                for (result in value) {
                    val note = result.toObject(Note::class.java)
                    allNotesList.add(note)
                }
                mAdapter.notifyDataSetChanged()
            }

        }
    }

    override fun onStop() {
        super.onStop()
        noteListener.remove()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
            setHasFixedSize(true)
        }

        binding.goToAddFragmentBtn.setOnClickListener {
            if(FirebaseAuth.getInstance().currentUser == null){
                view.findNavController().navigate(R.id.action_fragmentNotes_to_fragmentMe)
            }
            else{
                view.findNavController().navigate(R.id.action_fragmentNotes_to_fragmentAdd)
            }
        }

    }


}