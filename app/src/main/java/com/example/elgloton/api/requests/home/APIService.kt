package com.example.elgloton.api.requests.home

import com.example.elgloton.api.models.home.Food
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("home/{category}/")
    fun getFood(@Path("category") category: String): Call<Food>
}