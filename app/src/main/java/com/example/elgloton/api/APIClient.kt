package com.example.elgloton.api

import com.example.elgloton.api.requests.home.APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object APIClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://161.132.47.139/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(APIService::class.java)
}