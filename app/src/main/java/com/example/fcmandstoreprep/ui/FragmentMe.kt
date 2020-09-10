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
import com.example.fcmandstoreprep.adapters.MyNotesAdapter
import com.example.fcmandstoreprep.databinding.FragmentMeBinding
import com.example.fcmandstoreprep.dialogs.LogoutDialog
import com.example.fcmandstoreprep.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FragmentMe : Fragment(R.layout.fragment_me) {

    private lateinit var binding: FragmentMeBinding
    private val mAuth = FirebaseAuth.getInstance()
    private var notesList = mutableListOf<Note>()
    private val mAdapter = MyNotesAdapter(notesList)
    private val notesCollection = FirebaseFirestore.getInstance().collection("Notes")
    private lateinit var noteListener: ListenerRegistration

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "My profile"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

            val query = notesCollection.whereEqualTo("userId", mAuth.currentUser?.uid)
                .orderBy("timestamp", Query.Direction.DESCENDING)

        noteListener = query.addSnapshotListener { value, error ->
            if(error != null) {
                return@addSnapshotListener
            }

            if(value != null) {

                // we clear the list to avoid duplicate data
                notesList.clear()

                for (result in value) {
                    val note = result.toObject(Note::class.java)
                    notesList.add(note)
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

        if (isUserLogged()) {
            binding.welcomeUserTextView.text = "Welcome ${mAuth.currentUser?.displayName}!"
        } else {
            view.findNavController().navigate(R.id.action_fragmentMe_to_fragmentSignIn2)
        }

        binding.logOutBtn.setOnClickListener {
            val dialog = LogoutDialog()
            fragmentManager?.let { it1 -> dialog.show(it1, "tag") }
        }


    }

    private fun isUserLogged(): Boolean = mAuth.currentUser != null


}