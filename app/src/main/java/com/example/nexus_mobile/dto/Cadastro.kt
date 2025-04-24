package com.example.nexus_mobile.dto

data class CadastroRequest(
    val nome: String,
    val email: String,
    val senha: String
)

class CadastroResponse() {
    var nome: String = ""
    var email: String = ""

    override fun toString(): String {
        return "CadastroResponse(nome='$nome', email='$email')"
    }
}