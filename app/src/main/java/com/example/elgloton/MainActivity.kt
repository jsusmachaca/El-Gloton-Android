package com.example.elgloton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elgloton.api.APIClient
import com.example.elgloton.api.adapters.FoodAdapter
import com.example.elgloton.api.models.Food
import com.example.elgloton.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var currentCategory = "foods"
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        binding.btnFoods.setOnClickListener {
            currentCategory = "foods"
            loadFoodData(currentCategory)
        }

        binding.btnDrinks.setOnClickListener {
            currentCategory = "drinks"
            loadFoodData(currentCategory)
        }

        binding.btnSoups.setOnClickListener {
            currentCategory = "soups"
            loadFoodData(currentCategory)
        }

        loadFoodData(currentCategory)
    }

    private fun loadFoodData(category: String) {
        val retrofit = APIClient.apiService.getFood(category)
        retrofit.enqueue(object : Callback<Food> {
            override fun onResponse(call: Call<Food>, response: Response<Food>) {
                Log.d("API Response", response.body().toString())
                val foodItems = response.body()
                if (foodItems != null) {
                    val adapter = FoodAdapter(foodItems)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Food>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Error al consultar la API",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

}