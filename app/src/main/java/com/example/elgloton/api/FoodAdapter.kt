package com.example.elgloton.api

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elgloton.R
import com.example.elgloton.api.models.FoodItem
import com.squareup.picasso.Picasso


class FoodAdapter(private val foodItems: List<FoodItem>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.foodImage)
        val foodName: TextView = itemView.findViewById(R.id.foodName)
        val foodPrice: TextView = itemView.findViewById(R.id.foodPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.foot, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodItem = foodItems[position]

        holder.foodName.text = foodItem.food_name
        holder.foodPrice.text = "S/.${foodItem.price}"
        Picasso.get().load(foodItem.image).into(holder.foodImage)
    }

    override fun getItemCount(): Int {
        return foodItems.size
    }
}
