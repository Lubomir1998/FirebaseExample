package com.example.fcmandstoreprep.dialogs

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fcmandstoreprep.R
import com.example.fcmandstoreprep.databinding.LogoutConfirmationDialogBinding
import com.firebase.ui.auth.AuthUI

class LogoutDialog: AppCompatDialogFragment() {

    private lateinit var binding: LogoutConfirmationDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = LogoutConfirmationDialogBinding
            .inflate(LayoutInflater.from(requireContext()))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)


        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        binding.yesBtn.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext()).addOnCompleteListener {
                NavHostFragment.findNavController(this).navigate(R.id.fragmentSignIn)
                dismiss()
            }
        }



        return builder.create()
    }


}