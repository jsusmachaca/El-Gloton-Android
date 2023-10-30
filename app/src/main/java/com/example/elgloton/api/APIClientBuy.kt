package com.example.elgloton.api

import android.content.Context
import android.widget.Toast
import com.example.elgloton.api.requests.APIBuy
import com.example.elgloton.api.requests.BuyRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClientBuy {
    fun init(context: Context, foodId: Int, quantity: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.11:8000/") // Reemplaza con la URL de tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(APIBuy::class.java) // Define tu propia interfaz de servicio

        val buyRequest = BuyRequest(quantity)

        val sharedPreferences = context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        val authToken = ("Bearer " + sharedPreferences.getString("access_token", ""))
        val call = apiService.buyFood(buyRequest, foodId, authToken)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Se ha agregado al carrito", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()

            }
        })
    }
}