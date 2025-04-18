package com.example.nexus_mobile.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.RetrofitClient
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.dto.CurtidaCriacaoDto
import com.example.nexus_mobile.uiState.CursoUiState
import com.example.nexus_mobile.utils.TokenManager
import kotlinx.coroutines.launch

class FavoritosViewModel(application: Application) : AndroidViewModel(application) {
    private val api = RetrofitClient.create(application.applicationContext)

    private val _favoritos = mutableStateListOf<Int>()
    val favoritos: List<Int> get() = _favoritos

    private val _cursosFavoritos = mutableStateListOf<CursoDto>()
    val cursosFavoritos: List<CursoDto> get() = _cursosFavoritos

    fun carregarFavoritos(idAssociado: Int) {
        viewModelScope.launch {
            try {
                val cursos = api.getFavoritosDoUsuario(idAssociado)
                _cursosFavoritos.clear()
                _cursosFavoritos.addAll(cursos)
                _favoritos.clear()
                _favoritos.addAll(cursos.map { it.id}) // cuidado com o nome certo do campo!
            } catch (e: Exception) {
                Log.e("FavoritosViewModel", "Erro ao buscar favoritos", e)
            }
        }
    }
    fun alterarFavorito(idAssociado: Int, cursoId: Int) {
        viewModelScope.launch {
            try {
                // Obtendo o token armazenado
                val token = TokenManager.getToken(getApplication<Application>())

                // Verificando se o token é válido
                if (token != null && token.isNotEmpty()) {
                    // Caso o curso já esteja nos favoritos, descurtir
                    if (_favoritos.contains(cursoId)) {
                        api.descurtirCurso("Bearer $token", idAssociado, cursoId) // Passando o token na requisição
                        _favoritos.remove(cursoId)
                    } else {
                        // Caso o curso não esteja nos favoritos, curtir
                        api.curtirCurso("Bearer $token", CurtidaCriacaoDto(idAssociado, cursoId)) // Passando o token na requisição
                        _favoritos.add(cursoId)
                    }
                } else {
                    Log.e("FavoritosViewModel", "Token não encontrado ou expirado")
                }
            } catch (e: Exception) {
                Log.e("FavoritosViewModel", "Erro ao alterar favorito", e)
            }
        }
    }
}
