package com.example.nexus_mobile.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.RetrofitClient
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.uiState.CursoUiState
import com.example.nexus_mobile.utils.TokenManager
import kotlinx.coroutines.launch
class CursoViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val api = RetrofitClient.create(application)

    private val _cursos = mutableStateListOf<CursoDto>()
    val cursos: List<CursoDto> get() = _cursos

    private val _uiState = mutableStateOf<CursoUiState>(CursoUiState.Loading)
    val uiState: State<CursoUiState> get() = _uiState

    fun carregarCursos(usuarioId: Int) {
        viewModelScope.launch {
            _uiState.value = CursoUiState.Loading
            try {
                val token = TokenManager.getToken(getApplication<Application>())

                if (token != null && token.isNotEmpty()) {
                    val cursosRecebidos = api.getCursos("Bearer $token", usuarioId)
                    _cursos.clear()
                    _cursos.addAll(cursosRecebidos)

                    _uiState.value = CursoUiState.Success(cursosRecebidos)
                    Log.d("CursoViewModel", "Cursos carregados com sucesso")
                } else {
                    _uiState.value = CursoUiState.Error("Token não encontrado ou expirado")
                }
            } catch (e: Exception) {
                _uiState.value = CursoUiState.Error("Erro ao buscar cursos: ${e.message}")
                Log.e("CursoViewModel", "Erro ao buscar cursos", e)
            }
        }
    }

    fun carregarCursosPorCategoria(idAssociado: Int, categoria: String) {
        viewModelScope.launch {
            try {
                val cursosRecebidos = api.getCursosPorCategoria(idAssociado, categoria)
                _cursos.clear()
                _cursos.addAll(cursosRecebidos)
                Log.d("CursoViewModel", "Cursos da categoria '$categoria' carregados com sucesso")
            } catch (e: Exception) {
                Log.e("CursoViewModel", "Erro ao buscar cursos por categoria", e)
            }
        }
    }

//    fun carregarCursoPorId(usuarioId: Int, cursoId: Int) {
//        viewModelScope.launch {
//            _uiState.value = CursoUiState.Loading
//            try {
//                // Se houver um endpoint para pegar um curso específico
//                val curso = api.getCursoPorId(usuarioId, cursoId) // Ajuste para o endpoint correto
//
//                if (curso != null) {
//                    _uiState.value = CursoUiState.Success(curso)
//                } else {
//                    _uiState.value = CursoUiState.Error("Curso não encontrado")
//                }
//            } catch (e: Exception) {
//                _uiState.value = CursoUiState.Error("Erro ao buscar curso: ${e.message}")
//            }
//        }
//    }
}