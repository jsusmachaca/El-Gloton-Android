package com.example.elgloton.components.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.elgloton.R

object DialogPay {
    fun showNumberInputDialog(context: Context, onNumberEntered: (Int) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.pay, null)
        val orderTable = dialogView.findViewById<EditText>(R.id.orderTable)
        val okButton = dialogView.findViewById<Button>(R.id.okPay)

        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.show()

        okButton.setOnClickListener {
            val enteredText = orderTable.text.toString()
            if (enteredText.isNotEmpty()) {

                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Confirmación")
                    builder.setMessage("¿Está seguro de realizar esta acción?")

                    builder.setPositiveButton("Sí") { _, _ ->
                        // El usuario confirmó, procede con el DialogPay
                        val enteredNumber = enteredText.toInt()
                        onNumberEntered(enteredNumber)
                        dialog.dismiss()
                    }

                    builder.setNegativeButton("No") { _, _ ->
                        dialog.dismiss()
                    }

                    val dialog = builder.create()
                    dialog.show()


            } else {
                Toast.makeText(context, "Ingrese un número válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}