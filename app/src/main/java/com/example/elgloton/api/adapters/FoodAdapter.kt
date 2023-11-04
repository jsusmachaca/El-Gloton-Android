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
import com.example.elgloton.api.models.home.FoodItem
import com.example.elgloton.components.dialogs.DialogFood
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
        holder.foodPrice.text = "S/. ${foodItem.price}"
        Picasso.get()
            .load(foodItem.image)
            .into(holder.foodImage)

        holder.foodPrice.setOnClickListener {
            DialogFood.showNumberInputDialog(holder.itemView.context) { enterNumber ->
                APIClientBuy.init(context, id, enterNumber)
            }
        }
    }

    override fun getItemCount(): Int {
        return foodItems.size
    }
}
