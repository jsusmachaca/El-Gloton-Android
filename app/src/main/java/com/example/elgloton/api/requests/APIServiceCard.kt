package com.example.elgloton.api.requests

import com.example.elgloton.api.models.FoodCardItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface APIServiceCard {
    @GET("user/dashboard/")
    fun getFoodCardItems(
        @Header("Authorization") token: String): Call<List<FoodCardItem>>

}