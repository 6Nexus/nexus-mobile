package com.example.nexus_mobile.data.model.login

data class LoginRequest(
    val email: String,
    val senha: String
) {
    override fun toString(): String {
        return "LoginRequest(email='$email', senha='$senha')"
    }
}