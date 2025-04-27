package com.example.nexus_mobile.viewModel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.RetrofitLogin
import com.example.nexus_mobile.api.LoginApi
import com.example.nexus_mobile.dto.CadastroRequest
import com.example.nexus_mobile.dto.CadastroResponse
import com.example.nexus_mobile.utils.TokenManager
import kotlinx.coroutines.launch

class PerfilViewModel (application: Application) : AndroidViewModel(application){

    var nome by mutableStateOf("")
    var email by mutableStateOf("")
    var senha by mutableStateOf("")
    var isCarregando by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var perfilResponde by mutableStateOf<CadastroResponse?>(null)

    private val context = getApplication<Application>().applicationContext
    private val api = RetrofitLogin.create(context)
    //private val api: LoginApi = RetrofitLogin.create(context)

    fun atualizarPerfil (id: Int, nome: String, email: String, senha: String, context: Context) {
        isCarregando = true
        errorMessage = null

        val cadastroRequest = CadastroRequest(nome, email, senha)


        viewModelScope.launch {
            try {
                val response = api.atualizarUsuario(cadastroRequest, id)
                if (response.isSuccessful) {
                    perfilResponde = response.body()
                } else {
                    errorMessage = "Erro ao atualizar perfil: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Erro ao atualizar perfil: ${e.message}"
            } finally {
                isCarregando = false
            }
        }
    }

}