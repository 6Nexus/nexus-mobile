package com.example.nexus_mobile.viewModel

import android.app.Application
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.nexus_mobile.RetrofitClient
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.dto.MatriculaRequest
import com.example.nexus_mobile.uiState.CursoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MatriculaViewModel(application: Application) : AndroidViewModel(application) {
    private val api = RetrofitClient.create(application)

    private val _matriculaRealizada = MutableStateFlow(false)
    val matriculaRealizada: StateFlow<Boolean> = _matriculaRealizada

    fun matricular(usuarioId: Int, cursoId: Int) {
        viewModelScope.launch {
            try {
                val response = api.matricular(MatriculaRequest(usuarioId, cursoId))
                if (response.isSuccessful) {
                    _matriculaRealizada.value = true
                } else {
                    Log.e("MatriculaViewModel", "Erro ao matricular: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("MatriculaViewModel", "Exceção ao matricular", e)
            }
        }
    }

    fun resetarStatus() {
        _matriculaRealizada.value = false
    }
}