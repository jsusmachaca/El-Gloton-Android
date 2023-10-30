package com.example.elgloton.api

import com.example.elgloton.api.requests.auth.APIServiceAuth
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIAuth {
    val retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.1.11:8000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    val authService = retrofit.create(APIServiceAuth::class.java)
}