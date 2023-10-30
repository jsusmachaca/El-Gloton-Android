package com.example.elgloton.api

import android.content.Context
import android.widget.Toast
import com.example.elgloton.api.models.dashboard.Order
import com.example.elgloton.api.requests.dashboard.APIDelete
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClientDel {

    fun init(context: Context, orderList: MutableList<Order>, foodId: Int, position: Int, onDeleteComplete: OnDeleteComplete) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.11:8000/") // Reemplaza con la URL de tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(APIDelete::class.java) // Define tu propia interfaz de servicio

        val sharedPreferences = context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        val authToken = ("Bearer " + sharedPreferences.getString("access_token", null))
        val call = apiService.deleteFood(foodId, authToken)

        println("Token token token => $authToken")
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    orderList.removeAt(position)
                    onDeleteComplete(position)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()

            }
        })
    }
}

typealias OnDeleteComplete = (Int) -> Unit