package com.example.elgloton.components.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.elgloton.R

object DialogFood {
    fun showNumberInputDialog(context: Context, onNumberEntered: (Int) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.buy, null)
        val numberEditText = dialogView.findViewById<EditText>(R.id.numberEditText)
        val okButton = dialogView.findViewById<Button>(R.id.okButton)

        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.show()

        okButton.setOnClickListener {
            val enteredText = numberEditText.text.toString()
            if (enteredText.isNotEmpty()) {
                val enteredNumber = enteredText.toInt()
                onNumberEntered(enteredNumber)
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Ingrese un número válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}