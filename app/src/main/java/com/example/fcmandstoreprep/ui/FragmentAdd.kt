package com.example.fcmandstoreprep.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fcmandstoreprep.MainActivity
import com.example.fcmandstoreprep.R
import com.example.fcmandstoreprep.databinding.FragmentAddBinding
import com.example.fcmandstoreprep.model.Note
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class FragmentAdd: Fragment(R.layout.fragment_add) {

    private lateinit var binding: FragmentAddBinding
    private val noteRef = FirebaseFirestore.getInstance().collection("Notes").document()


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Add note"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_fragmentAdd_to_fragmentNotes)
        }

        binding.addBtn.setOnClickListener {
            addNewNote()
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun addNewNote(){
        if(binding.titleEditText.text.isEmpty() || binding.descriptionEditText.text.isEmpty()){
            Snackbar.make(requireView(), "All fields must be filled!", Snackbar.LENGTH_SHORT).show()
        }
        else{
            val userName = FirebaseAuth.getInstance().currentUser?.displayName
            val title = binding.titleEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            val note = Note(userName!!, title, description, userId)

            noteRef.set(note).addOnSuccessListener {
                binding.titleEditText.text.clear()
                binding.descriptionEditText.text.clear()
                Snackbar.make(requireView(), "Note added", Snackbar.LENGTH_SHORT).show()
            }

        }

    }

}