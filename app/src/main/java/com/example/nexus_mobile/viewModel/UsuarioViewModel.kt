package com.example.nexus_mobile.viewModel

import android.app.Application
import android.content.Context
import androidx.annotation.OptIn
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.nexus_mobile.utils.UsuarioManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val _nome = MutableStateFlow("")
    val nome: StateFlow<String> = _nome

    private val _userId = MutableStateFlow(-1)
    val userId: StateFlow<Int> = _userId  // Alterado para userId para consistência

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    init {
        carregarUsuario()
    }

    fun setUserData(nome: String, email: String, userId: Int) {
        _nome.value = nome
        _email.value = email
        _userId.value = userId
        salvarUsuario(userId, nome, email)
    }

    fun salvarUsuario(userId: Int, nome: String, email: String) {
        UsuarioManager.salvarUsuario(context, userId, nome, email)
    }

    @OptIn(UnstableApi::class)
    fun carregarUsuario() {
        _nome.value = UsuarioManager.getUserName(context)
        _email.value = UsuarioManager.getUserEmail(context)
        _userId.value = UsuarioManager.getUserId(context)

        Log.d("UsuarioViewModel", "Usuário carregado: id=${_userId.value}, nome=${_nome.value}")
    }

    fun limparUsuario() {
        _nome.value = ""
        _email.value = ""
        _userId.value = 0
        UsuarioManager.limparDados(context)
        Log.d("UsuarioViewModel", "Dados do usuário limpos")
    }
}
