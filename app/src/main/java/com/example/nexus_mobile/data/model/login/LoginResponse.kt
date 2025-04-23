package com.example.nexus_mobile.data.model.login

data class LoginResponse(
    val userId: Int,
    val nome: String,
    val email: String,
    val token: String
)