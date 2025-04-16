package com.example.nexus_mobile.data.model.cadastro

class CadastroResponse {
    var nome: String = ""
    var email: String = ""

    override fun toString(): String {
        return "CadastroResponse(nome='$nome', email='$email')"
    }
}


// retorno do json
// {
//	"id": 11,
//	"nome": "nome_f7b2e7c255b0",
//	"email": "fernanda@gmail.com",
//	"telefone": "telefone_0ced8481d804",
//	"aprovado": false
// }