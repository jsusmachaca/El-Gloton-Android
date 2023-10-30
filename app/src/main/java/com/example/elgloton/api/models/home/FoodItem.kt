package com.example.elgloton.api.models.home

data class FoodItem(
    val categorie: Int,
    val description: String,
    val food_name: String,
    val id: Int,
    val image: String,
    val price: String
)