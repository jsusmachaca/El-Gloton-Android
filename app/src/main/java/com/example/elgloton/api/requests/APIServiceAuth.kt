package com.example.elgloton.api.requests

import com.example.elgloton.api.models.AuthResponse
import com.example.elgloton.api.models.Food
import com.example.elgloton.api.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIServiceAuth {
    @POST("authentication/login/")
    fun login(@Body user: User): Call<AuthResponse>
}