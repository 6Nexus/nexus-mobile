package com.example.nexus_mobile.dto

data class LoginRequest(
    val email: String,
    val senha: String
)

data class LoginResponse(
    val userId: Int,
    val nome: String,
    val email: String,
    val token: String
)