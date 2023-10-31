package com.example.elgloton.api.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.elgloton.R
import com.example.elgloton.api.APIClientDel
import com.example.elgloton.api.models.dashboard.FoodCard
import com.example.elgloton.api.models.dashboard.FoodCardItem
import com.example.elgloton.api.models.dashboard.Order
import com.squareup.picasso.Picasso


class FoodCardAdapter(private val context: Context, private val orderList: MutableList<Order>) : RecyclerView.Adapter<FoodCardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.foodCardImage)
        val foodName: TextView = itemView.findViewById(R.id.foodCardName)
        val foodPrice: TextView = itemView.findViewById(R.id.foodCardPrice)
        private val deleteImage: ImageView = itemView.findViewById(R.id.deleteIcon)

        init {
            deleteImage.setOnClickListener {
                val order = orderList[adapterPosition] // Obtén el objeto Order en el que se hizo clic
                val position = adapterPosition
                val foodId = order.id // Obtén el ID del alimento
                sendRequestToDeleteFood(foodId, position, context)
            }

        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sendRequestToDeleteFood(foodId: Int, position: Int, context: Context) {
        APIClientDel.init(context, orderList, foodId, position) { deletePosition ->
            notifyItemRemoved(position)
            Toast.makeText(
                context,
                "Elemento eliminado",
                Toast.LENGTH_SHORT
            ).show()
        }
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