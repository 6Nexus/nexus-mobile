package com.example.nexus_mobile.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsuarioViewModel : ViewModel() {


    private val _nome = MutableStateFlow("")
    val nome: StateFlow<String> = _nome

    private val _userId = MutableStateFlow(0)
    val id: StateFlow<Int> = _userId

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    fun setUserData(nome: String, email: String, id: Int) {
        _nome.value = nome
        _email.value = email
        _userId.value = id
    }

    fun limparUsuario() {
        _nome.value = ""
        _email.value = ""
        _userId.value = 0
    }
}