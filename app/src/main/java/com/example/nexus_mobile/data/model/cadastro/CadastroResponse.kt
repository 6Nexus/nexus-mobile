package com.example.nexus_mobile.data.model.cadastro

class CadastroResponse(
    var nome: String = "",
    var email: String = "",
    val idAssociado: Int,
) {


    override fun toString(): String {
        return "CadastroResponse(nome='$nome', email='$email')"
    }
}


