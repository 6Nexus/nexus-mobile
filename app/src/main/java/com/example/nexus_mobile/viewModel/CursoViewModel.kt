package com.example.nexus_mobile.viewModel

import android.util.Log
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.RetrofitClient
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.uiState.CursoUiState
import kotlinx.coroutines.launch

class CursoViewModel : ViewModel() {
    private val _cursos = mutableStateListOf<CursoDto>()
    val cursos: List<CursoDto> get() = _cursos
    private val _uiState = mutableStateOf<CursoUiState>(CursoUiState.Loading)
    val uiState: State<CursoUiState> get() = _uiState


    fun carregarCursos(usuarioId: Int) {
        viewModelScope.launch {
            try {
                val cursosRecebidos = RetrofitClient.api.getCursos(usuarioId)
                _cursos.clear()
                _cursos.addAll(cursosRecebidos)
            } catch (e: Exception) {
                Log.e("CursoViewModel", "Erro ao buscar cursos", e)
            }
        }
    }

    fun carregarCursoPorId(usuarioId: Int, cursoId: Int) {
        viewModelScope.launch {
            _uiState.value = CursoUiState.Loading
            try {
                val cursos = RetrofitClient.api.getCursos(usuarioId)
                val cursoEncontrado = cursos.find { it.id == cursoId }
                if (cursoEncontrado != null) {
                    _uiState.value = CursoUiState.Success(cursoEncontrado)
                } else {
                    _uiState.value = CursoUiState.Error("Curso não encontrado")
                }
            } catch (e: Exception) {
                _uiState.value = CursoUiState.Error("Erro ao buscar curso: ${e.message}")
            }
        }
    }
}


class FavoritosViewModel : ViewModel() {
    private val _favoritos = mutableStateListOf<Int>()
    val favoritos: List<Int> get() = _favoritos

    fun alterarFavorito(cursoId: Int) {
        if (_favoritos.contains(cursoId)) {
            _favoritos.remove(cursoId)
        } else {
            _favoritos.add(cursoId)
        }
    }
}