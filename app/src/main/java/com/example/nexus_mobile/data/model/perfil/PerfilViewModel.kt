package com.example.nexus_mobile.data.model.perfil

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.data.model.services.AssociadoApiService
import com.example.nexus_mobile.data.model.services.TokenJWT
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PerfilViewModel : ViewModel() {

    var nome by mutableStateOf("")
    var email by mutableStateOf("")
    var senha by mutableStateOf("")
    var isCarregando by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var perfilResponse by mutableStateOf<PerfilResponse?>(null)

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://88:8080/") // substitua pelo seu IP se necessário
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val associadoService = retrofit.create(AssociadoApiService::class.java)

    fun carregarDadosUsuario(context: Context) {
        nome = TokenJWT.recuperarNomeUsuario(context) ?: ""
        email = TokenJWT.recuperarEmailUsuario(context) ?: ""
    }

    fun atualizarPerfil(context: Context) {
        viewModelScope.launch {
            isCarregando = true
            errorMessage = null
            try {
                val perfilRequest = PerfilRequest(nome, email, senha)
                val idUsuario = TokenJWT.recuperarIdUsuario(context)
                val response = associadoService.atualizarPerfil(idUsuario, perfilRequest)

                TokenJWT.salvarDadosUsuario(context, response.nome, response.email)
                perfilResponse = response

            } catch (e: Exception) {
                errorMessage = "Erro ao atualizar perfil: ${e.message}"
            } finally {
                isCarregando = false
            }
        }
    }
}
