package com.example.nexus_mobile.data.model.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.nexus_mobile.data.model.login.LoginResponse
import com.example.nexus_mobile.data.model.services.AssociadoService
import okhttp3.OkHttpClient
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.data.model.cadastro.CadastroResponse
import com.example.nexus_mobile.data.model.cadastro.CadastroViewModel
import com.example.nexus_mobile.data.model.services.TokenJWT
import com.example.nexus_mobile.data.model.services.TokenJWT.salvarToken
import com.example.nexus_mobile.data.model.services.api
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor


class LoginViewModel : ViewModel() {

   // var nome by mutableStateOf("")
    var email by mutableStateOf("")
    var senha by mutableStateOf("")
    var isCarregando by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)
    var loginResponse by mutableStateOf<LoginResponse?>(null)

    fun fazerLogin(context: Context) {
        viewModelScope.launch {
            isCarregando = true
            errorMessage = null
            try {
                val loginRequest = LoginRequest(email, senha)


                // Cria uma instância do Retrofit com o contexto
                val associadoService = api.criarApi(context)

                // Faz a chamada de login
                val response = associadoService.login(loginRequest)
                Log.d("LoginViewModel", "Fazendo login com email: $email e senha: $senha id: ${response.userId}")

                // Salva o token
                TokenJWT.salvarToken(context, response.token)

                // Pegar nome do usuario
                val nomeUsuario = response.nome
                TokenJWT.salvarDadosUsuario(context, nomeUsuario, email, response.userId)


                // Atualiza estados
                loginResponse = response
                loginSuccess = true

            } catch (e: Exception) {
                Log.d("LOGIN_DEBUG", "Erro ao fazer login", e)
                errorMessage = "Erro ao fazer login: ${e.message}"
                loginSuccess = false
            } finally {
                isCarregando = false
            }
        }
    }

}

