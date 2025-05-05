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

    private val api = RetrofitClient.getCursoApi(application.applicationContext)

    private val _favoritos = mutableStateListOf<Int>()
    val favoritos: List<Int> get() = _favoritos

    private val _cursosFavoritos = mutableStateListOf<CursoDto>()
    val cursosFavoritos: List<CursoDto> get() = _cursosFavoritos


    fun carregarFavoritos(idAssociado: Int) {
        viewModelScope.launch {
            try {
                Log.d("FavoritosViewModel", "Iniciando carregamento de favoritos para idAssociado=$idAssociado")
                val cursos = api.getFavoritosDoUsuario(idAssociado)

                if (cursos == null) {
                    Log.w("FavoritosViewModel", "API retornou null ao buscar favoritos!")
                } else {
                    Log.d("FavoritosViewModel", "API retornou ${cursos.size} curso(s) favorito(s)")
                    cursos.forEach {
                        Log.d("FavoritosViewModel", "Curso -> id=${it.id}, titulo=${it.titulo}, categoria=${it.categoria}")
                    }
                }

                _cursosFavoritos.clear()
                _cursosFavoritos.addAll(cursos ?: emptyList())

                _favoritos.clear()
                _favoritos.addAll((cursos ?: emptyList()).map { it.id })

                Log.d("FavoritosViewModel", "Favoritos atualizados: $_favoritos")
            } catch (e: Exception) {
                Log.e("FavoritosViewModel", "Erro ao buscar favoritos", e)
            }
        }
    }

    fun alterarFavorito(idAssociado: Int, cursoId: Int) {
        viewModelScope.launch {
            try {
                val token = TokenManager.getToken(getApplication<Application>())
                Log.d("FavoritosViewModel", "Alterando favorito para cursoId=$cursoId com idAssociado=$idAssociado")

                if (token != null && token.isNotEmpty()) {
                    if (_favoritos.contains(cursoId)) {
                        Log.d("FavoritosViewModel", "Curso $cursoId já é favorito. Enviando requisição para remover.")
                        api.descurtirCurso("Bearer $token", idAssociado, cursoId)
                        _favoritos.remove(cursoId)
                    } else {
                        Log.d("FavoritosViewModel", "Curso $cursoId não é favorito. Enviando requisição para curtir.")
                        api.curtirCurso("Bearer $token", CurtidaCriacaoDto(idAssociado, cursoId))
                        _favoritos.add(cursoId)
                    }

                    // Recarregar favoritos para refletir mudanças
                    Log.d("FavoritosViewModel", "Recarregando favoritos após alteração")
                    carregarFavoritos(idAssociado)
                } else {
                    Log.e("FavoritosViewModel", "Token não encontrado ou expirado")
                }
            } catch (e: Exception) {
                Log.e("FavoritosViewModel", "Erro ao alterar favorito", e)
            }
        }
    }
}
