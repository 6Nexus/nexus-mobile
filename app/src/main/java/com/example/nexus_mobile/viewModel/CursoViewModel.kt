package com.example.nexus_mobile.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.nexus_mobile.RetrofitClient
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.dto.MatriculaRequest
import com.example.nexus_mobile.dto.Modulo
import com.example.nexus_mobile.uiState.CursoUiState
import com.example.nexus_mobile.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CursoViewModel(application: Application) : AndroidViewModel(application) {

    private val api = RetrofitClient.create(application)

    private val _cursos = mutableStateListOf<CursoDto>()
    val cursos: List<CursoDto> get() = _cursos

    private val _matriculaRealizada = MutableStateFlow(false)
    val matriculaRealizada: StateFlow<Boolean> = _matriculaRealizada

    private val _uiState = mutableStateOf<CursoUiState>(CursoUiState.Loading)
    val uiState: State<CursoUiState> get() = _uiState

    private val _modulos = MutableStateFlow<List<Modulo>>(emptyList())
    val modulos: StateFlow<List<Modulo>> = _modulos


    fun carregarCursos(usuarioId: Int) {
        viewModelScope.launch {
            _uiState.value = CursoUiState.Loading
            try {
                val token = TokenManager.getToken(getApplication<Application>())
                if (!token.isNullOrEmpty()) {
                    val cursosRecebidos = api.getCursos("Bearer $token", usuarioId)
                    _cursos.clear()
                    _cursos.addAll(cursosRecebidos)
                    _uiState.value = CursoUiState.Success(cursosRecebidos)
                } else {
                    _uiState.value = CursoUiState.Error("Token não encontrado ou expirado")
                }
            } catch (e: Exception) {
                _uiState.value = CursoUiState.Error("Erro ao buscar cursos: ${e.message}")
            }
        }
    }

    fun carregarCursosPorCategoria(idAssociado: Int, categoria: String) {
        viewModelScope.launch {
            try {
                val cursosRecebidos = api.getCursosPorCategoria(idAssociado, categoria)
                _cursos.clear()
                _cursos.addAll(cursosRecebidos)
            } catch (e: Exception) {
                Log.e("CursoViewModel", "Erro ao buscar cursos por categoria", e)
            }
        }
    }

    fun carregarCursoPorId(usuarioId: Int, cursoId: Int) {
        viewModelScope.launch {
            _uiState.value = CursoUiState.Loading
            try {
                val curso = api.getCursoPorId(cursoId = cursoId, usuarioId = usuarioId)
                _uiState.value = CursoUiState.SuccessCurso(curso)
            } catch (e: Exception) {
                _uiState.value = CursoUiState.Error("Erro ao carregar o curso: ${e.message}")
            }
        }
    }

    @OptIn(UnstableApi::class)
    fun matricular(usuarioId: Int, cursoId: Int) {
        viewModelScope.launch {
            try {
                val response = api.matricular(MatriculaRequest(usuarioId, cursoId))
                if (response.isSuccessful) {
                    _matriculaRealizada.value = true
                    androidx.media3.common.util.Log.e("CursoViewModel", "matricula executada: ${response.code()}")
                } else {
                    androidx.media3.common.util.Log.e("CursoViewModel", "Erro ao matricular: ${response.code()}")
                }
            } catch (e: Exception) {
                androidx.media3.common.util.Log.e("CursoViewModel", "Exceção ao matricular", e)
            }
        }
    }

    fun carregarModulos(cursoId: Int) {
        viewModelScope.launch {
            try {
                val token = TokenManager.getToken(getApplication<Application>())
                if (!token.isNullOrEmpty()) {
                    val modulosRecebidos = api.getModulosPorCurso(cursoId, "Bearer $token")
                    _modulos.value = modulosRecebidos
                } else {
                    Log.e("CursoViewModel", "Token não encontrado ou expirado")
                }
            } catch (e: Exception) {
                Log.e("CursoViewModel", "Erro ao carregar módulos", e)
            }
        }
    }

    fun resetarStatus() {
        _matriculaRealizada.value = false
    }
}