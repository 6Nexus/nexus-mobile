package com.example.nexus_mobile.data.model.perfil

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.data.model.services.AssociadoService
import com.example.nexus_mobile.data.model.services.TokenJWT
import com.example.nexus_mobile.data.model.services.api
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

//    private val retrofit = Retrofit.Builder()
//        .baseUrl("http://88:8080/") // substitua pelo seu IP se necessário
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

   // private val associadoService = retrofit.create(AssociadoService::class.java)

    fun carregarDadosUsuario(context: Context) {
        nome = TokenJWT.recuperarNome(context) ?: ""
        email = TokenJWT.recuperarEmail(context) ?: ""
    }

    fun atualizarPerfil(context: Context) {
        viewModelScope.launch {
            isCarregando = true
            errorMessage = null
            try {
                val associadoService = api.criarApi(context)
                val perfilRequest = PerfilRequest(nome, email, senha)
                val idUsuario = TokenJWT.recuperarIdAssociado(context)
                val response = associadoService.atualizarPerfil(idUsuario, perfilRequest)

                TokenJWT.salvarDadosUsuario(context, response.nome, response.email, idUsuario)
                Log.d("PerfilViewModel", "Perfil atualizado com sucesso: $response")
                Log.d("PerfilViewModel", "Nome: ${response.nome} e Email: ${response.email}")
                perfilResponse = response

            } catch (e: Exception) {
                errorMessage = "Erro ao atualizar perfil: ${e.message}"
            } finally {
                isCarregando = false
            }
        }
    }
}
