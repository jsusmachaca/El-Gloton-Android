package com.example.elgloton.api.requests

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface APIDelete {
    @DELETE("user/dashboard/del/{foodId}/")
    fun deleteFood(@Path("foodId") foodId: Int, @Header("Authorization") authToken: String): Call<Void>
}