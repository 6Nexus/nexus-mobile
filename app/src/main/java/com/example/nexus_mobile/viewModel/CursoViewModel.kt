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
import androidx.lifecycle.application
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
import com.example.nexus_mobile.dto.Video


class CursoViewModel(application: Application) : AndroidViewModel(application) {

    private val api = RetrofitClient.getCursoApi(application)

    private val _cursos = mutableStateListOf<CursoDto>()
    val cursos: List<CursoDto> get() = _cursos

    private val _curso = mutableStateOf<CursoDto?>(null)
    val curso: State<CursoDto?> = _curso

    private val _matriculaRealizada = mutableStateOf(false)
    val matriculaRealizada: State<Boolean> = _matriculaRealizada

    private val _uiState = mutableStateOf<CursoUiState>(CursoUiState.Loading)
    val uiState: State<CursoUiState> get() = _uiState

    private val _modulos = MutableStateFlow<List<Modulo>>(emptyList())
    val modulos: StateFlow<List<Modulo>> = _modulos

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    private var isMatriculado = false

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
                _curso.value = curso
                _uiState.value = CursoUiState.SuccessCurso(curso)
                Log.d("CursoViewModel", "Curso carregado com sucesso: ${curso.titulo}")
            } catch (e: Exception) {
                Log.e("CursoViewModel", "Erro ao carregar o curso: ${e.message}", e)
                _uiState.value = CursoUiState.Error("Erro ao carregar o curso: ${e.message}")
            }
        }
    }

    @OptIn(UnstableApi::class)
    fun matricular(usuarioId: Int, cursoId: Int) {
        if (_matriculaRealizada.value) return

        viewModelScope.launch {
            try {
                val response = api.matricular(MatriculaRequest(usuarioId, cursoId))
                if (response.isSuccessful) {
                    _matriculaRealizada.value = true
                    Log.e("CursoViewModel", "matricula executada: ${response.code()}")
                } else {
                    Log.e("CursoViewModel", "Erro ao matricular: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CursoViewModel", "Exceção ao matricular", e)
            }
        }
    }


    fun verificarMatricula(idAssociado: Int, cursoId: Int) {
        viewModelScope.launch {
            try {
                val token = TokenManager.getToken(getApplication<Application>())
                val response = api.verificarMatricula("Bearer $token", idAssociado, cursoId)

                if (response.isSuccessful) {
                    val idMatricula = response.body()
                    if (idMatricula != null) {
                        Log.d("Curso", "Usuário já matriculado! ID: $idMatricula")
                        _matriculaRealizada.value = true
                    }
                } else if (response.code() == 404) {
                    Log.d("Curso", "Usuário ainda não está matriculado")
                    _matriculaRealizada.value = false
                } else {
                    Log.e("Curso", "Erro inesperado: ${response.code()}")
                    _matriculaRealizada.value = false
                }
            } catch (e: Exception) {
                Log.e("Curso", "Erro ao verificar matrícula: ${e.message}")
                _matriculaRealizada.value = false
            }
        }
    }


    fun carregarModulos(cursoId: Int) {
        viewModelScope.launch {
            try {
                val token = TokenManager.getToken(getApplication<Application>())
                if (!token.isNullOrEmpty()) {
                    val modulosRecebidos = api.getModulosPorCurso(cursoId, "Bearer $token")

                    // Verifique se a resposta é válida
                    if (modulosRecebidos.isNullOrEmpty()) {
                        Log.e("CursoViewModel", "Nenhum módulo encontrado para o curso")
                    } else {
                        _modulos.value = modulosRecebidos
                    }
                } else {
                    Log.e("CursoViewModel", "Token não encontrado ou expirado")
                }
            } catch (e: Exception) {
                Log.e("CursoViewModel", "Erro ao carregar módulos", e)
            }
        }
    }


    fun carregarVideos(moduloId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _erro.value = null
            try {
                val listaVideos = api.getVideosPorModulo(moduloId)
                _videos.value = listaVideos
            } catch (e: Exception) {
                _erro.value = "Erro ao carregar vídeos: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun resetarStatus() {
        _matriculaRealizada.value = false
    }
}