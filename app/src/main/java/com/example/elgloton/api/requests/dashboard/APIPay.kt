package com.example.elgloton.api.requests.dashboard

import com.example.elgloton.api.models.dashboard.CardReq
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface APIPay {
    @POST("user/dashboard/pay/{cardId}/")
    fun payCard(@Path("cardId") cardId: Int, @Body card: CardReq, @Header("Authorization") authToken: String): Call<Void>
}