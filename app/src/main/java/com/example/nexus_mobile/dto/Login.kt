package com.example.nexus_mobile.dto

data class LoginRequest(
    val email: String,
    val senha: String
)

data class LoginResponse(
    val token: String // Depende da sua API, pode ter mais campos
)