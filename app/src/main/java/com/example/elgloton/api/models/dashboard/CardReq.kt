package com.example.elgloton.api.models.dashboard

data class CardReq(
    val address_or_tables: String,
    val total_pay: String,
    val payed: Boolean
)
