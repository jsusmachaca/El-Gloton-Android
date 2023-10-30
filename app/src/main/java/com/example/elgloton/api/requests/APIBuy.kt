package com.example.elgloton.api.requests

import com.example.elgloton.api.models.AuthResponse
import com.example.elgloton.api.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface APIBuy {
    @POST("user/buy/{foodId}/")
    fun buyFood(@Body buyRequest: BuyRequest, @Path("foodId") foodId: Int, @Header("Authorization") authToken: String): Call<Void>
}