package com.example.elgloton.components.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.elgloton.R
import java.lang.ClassCastException

class LoginDialog: DialogFragment() {
    private lateinit var listener: LoginDialogListener

    interface LoginDialogListener {
        fun onLoginClick(username: String, password: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as LoginDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LoginDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_login, null)

        builder.setView(view)
            .setPositiveButton("Login") { _, _ ->
                val username = view.findViewById<EditText>(R.id.usernameEditText).text.toString()
                val password = view.findViewById<EditText>(R.id.passwordEditText).text.toString()
                listener.onLoginClick(username, password)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }
}