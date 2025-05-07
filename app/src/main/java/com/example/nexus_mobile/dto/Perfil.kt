package com.example.nexus_mobile.dto

data class PerfilRequest(
    val nome: String,
    val email: String,
    val senha: String
) {
    override fun toString(): String {
        return "PerfilRequest(nome='$nome', email='$email', senha='$senha')"
    }
}

data class PerfilResponse(
    val userId: Int,
    val nome: String,
    val email: String
)
