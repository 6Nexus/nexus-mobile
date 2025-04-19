package com.example.nexus_mobile.data.model.cadastro

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.data.model.services.TokenJWT
import com.example.nexus_mobile.data.model.services.api
import kotlinx.coroutines.launch

class CadastroViewModel : ViewModel() {

    var nome by mutableStateOf("")
    var email by mutableStateOf("")
    var senha by mutableStateOf("")

    var isCarregando by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var cadastroSuccess by mutableStateOf(false)
    var cadastroResponse by mutableStateOf<CadastroResponse?>(null)

    fun fazerCadastro(context: Context) {
        viewModelScope.launch {
            isCarregando = true
            errorMessage = null
            try {
                val cadastroRequest = CadastroRequest(nome, email, senha)

                Log.d("CadastroViewModel", "Fazendo cadastro com nome: $nome, email: $email")

                // Cria uma instância do Retrofit com o contexto
                val associadoService = api.criarApi(context)

                // Faz a chamada de login
                val response = associadoService.cadastrar(cadastroRequest)

                // Salvar dados do usuário (nome e email)
                TokenJWT.salvarDadosUsuario(context, nome, email)
                Log.d("CadastroViewModel", "Dados do usuário salvos: nome: $nome, email: $email")

                // Atualiza estados
                cadastroResponse = response
                cadastroSuccess = true

            } catch (e: Exception) {
                errorMessage = "Erro ao fazer cadastro: ${e.message}"
                cadastroSuccess = false
            } finally {
                isCarregando = false
            }
        }
    }

    fun carregarDadosUsuario(context: Context) {
        nome = TokenJWT.recuperarNome(context) ?: ""
        email = TokenJWT.recuperarEmail(context) ?: ""
        Log.d("TelaPerfil", "Nome carregado: $nome, Email carregado: $email")
    }

}