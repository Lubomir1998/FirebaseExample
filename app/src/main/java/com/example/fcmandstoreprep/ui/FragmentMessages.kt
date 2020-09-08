package com.example.fcmandstoreprep.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fcmandstoreprep.MainActivity
import com.example.fcmandstoreprep.R
import com.example.fcmandstoreprep.databinding.FragmentMessagesBinding

class FragmentMessages: Fragment(R.layout.fragment_messages) {

    private lateinit var binding: FragmentMessagesBinding


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Messages"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}