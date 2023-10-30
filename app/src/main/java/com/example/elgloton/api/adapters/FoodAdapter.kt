package com.example.elgloton.api.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elgloton.R
import com.example.elgloton.api.APIClientBuy
import com.example.elgloton.api.models.FoodItem
import com.example.elgloton.components.Dashboard
import com.example.elgloton.components.LoginDialog
import com.squareup.picasso.Picasso


class FoodAdapter(private val context: Context, private val foodItems: List<FoodItem>, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.foodImage)
        val foodName: TextView = itemView.findViewById(R.id.foodName)
        val foodPrice: Button = itemView.findViewById(R.id.foodPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.foot, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodItem = foodItems[position]
        val id = foodItem.id

        holder.foodName.text = foodItem.food_name
        holder.foodPrice.text = "S/.${foodItem.price}"
        Picasso.get()
            .load(foodItem.image)
            .into(holder.foodImage)

        println("Los items de home $foodItem")

        holder.foodPrice.setOnClickListener {

            showNumberInputDialog(holder.itemView.context) { enterNumber ->
                val sharedPreferences = context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)

                val token = sharedPreferences.getString("access_token", "")
                if (token != null && token.isNotEmpty()) {
                    APIClientBuy.init(context, id, enterNumber)
                } else {
                    val loginDialog = LoginDialog()
                    loginDialog.show(fragmentManager, "login_dialog")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return foodItems.size
    }


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
