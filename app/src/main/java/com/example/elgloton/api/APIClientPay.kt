package com.example.elgloton.api

import android.content.Context
import android.widget.Toast
import com.example.elgloton.api.models.dashboard.CardReq
import com.example.elgloton.api.models.home.BuyRequest
import com.example.elgloton.api.requests.dashboard.APIPay
import com.example.elgloton.api.requests.home.APIBuy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClientPay {
    fun init(context: Context, cardId: Int, address_or_tables: Int, total_pay: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://161.132.47.139/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(APIPay::class.java)

        val payRequest = CardReq("Mesa $address_or_tables", total_pay, false)

        val sharedPreferences = context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
        val authToken = ("Bearer " + sharedPreferences.getString("access_token", null))
        val call = apiService.payCard(cardId, payRequest, authToken)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Su pedido a la mesa $address_or_tables se a realizado con éxito.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "No puedes realizar pedidos mientras tengas una orden pendiente.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()

            }
        })
    }
}
