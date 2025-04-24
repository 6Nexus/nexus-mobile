package com.example.nexus_mobile.data.model.cadastro

data class CadastroRequest(
    val nome: String,
    val email: String,
    val senha: String,

) {

    override fun toString(): String {
        return "CadastroRequest(nome='$nome', email='$email', senha='$senha')"
    }

}