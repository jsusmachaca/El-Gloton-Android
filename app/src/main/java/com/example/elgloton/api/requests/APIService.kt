package com.example.elgloton.api.requests

import com.example.elgloton.api.models.Food
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("home/{category}/")
    fun getFood(@Path("category") category: String): Call<Food>
}