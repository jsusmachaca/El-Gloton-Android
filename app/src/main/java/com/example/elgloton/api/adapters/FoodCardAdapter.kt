package com.example.elgloton.api.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elgloton.R
import com.example.elgloton.api.models.FoodCardItem
import com.example.elgloton.api.models.Order
import com.squareup.picasso.Picasso


class FoodCardAdapter(private val orderList: List<Order>) : RecyclerView.Adapter<FoodCardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.foodCardImage)
        val foodName: TextView = itemView.findViewById(R.id.foodCardName)
        val foodPrice: TextView = itemView.findViewById(R.id.foodCardPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_food, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderList[position]

        holder.foodName.text = order.food.food_name
        holder.foodPrice.text = "S/. ${order.food.price} \t\t Cantidad: ${order.quantity}"

        Picasso.get()
            .load(order.food.food_image)
            .into(holder.foodImage)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

}