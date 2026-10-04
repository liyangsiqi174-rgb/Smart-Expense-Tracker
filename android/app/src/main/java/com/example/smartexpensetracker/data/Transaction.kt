package com.example.smartexpensetracker.data

data class Transaction(
    val id: Long,
    val amount: Double,
    val type: String,
    val category: String,
    val description: String,
    val date: String
)