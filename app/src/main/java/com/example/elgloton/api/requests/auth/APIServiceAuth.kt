package com.example.elgloton.api.requests.auth

import com.example.elgloton.api.models.auth.AuthResponse
import com.example.elgloton.api.models.auth.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIServiceAuth {
    @POST("authentication/login/")
    fun login(@Body user: User): Call<AuthResponse>
}