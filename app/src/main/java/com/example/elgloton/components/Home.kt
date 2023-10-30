package com.example.elgloton.components

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.elgloton.R
import com.example.elgloton.api.APIClient
import com.example.elgloton.api.adapters.FoodAdapter
import com.example.elgloton.api.models.Food
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnFoods: Button
    private lateinit var btnDrinks: Button
    private lateinit var btnSoups: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        var currentCategory = "foods"

        recyclerView = rootView.findViewById(R.id.recyclerView)
        btnFoods = rootView.findViewById(R.id.btnFoods)
        btnDrinks = rootView.findViewById(R.id.btnDrinks)
        btnSoups = rootView.findViewById(R.id.btnSoups)

        btnFoods.setOnClickListener {
            currentCategory = "foods"
            loadFoodData(currentCategory)
        }

        btnDrinks.setOnClickListener {
            currentCategory = "drinks"
            loadFoodData(currentCategory)
        }

        btnSoups.setOnClickListener {
            currentCategory = "soups"
            loadFoodData(currentCategory)
        }

        loadFoodData(currentCategory)

        return rootView
    }

    private fun loadFoodData(category: String) {
        val retrofit = APIClient.apiService.getFood(category)
        retrofit.enqueue(object : Callback<Food> {
            override fun onResponse(call: Call<Food>, response: Response<Food>) {
                val foodItems = response.body()
                if (foodItems != null) {
                    val adapter = FoodAdapter(foodItems)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Food>, t: Throwable) {
                Toast.makeText(
                    requireActivity(),
                    "Error al consultar la API",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}