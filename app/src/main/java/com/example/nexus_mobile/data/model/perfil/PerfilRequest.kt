package com.example.nexus_mobile.data.model.perfil

data class PerfilRequest(
    val nome: String,
    val email: String,
    val senha: String
) {
    override fun toString(): String {
        return "PerfilRequest(nome='$nome', email='$email', senha='$senha')"
    }
}