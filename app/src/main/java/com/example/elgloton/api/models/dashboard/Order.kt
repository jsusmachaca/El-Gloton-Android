package com.example.elgloton.api.models.dashboard

data class Order(
    val client: String,
    val food: FoodX,
    val id: Int,
    val quantity: Int
)

