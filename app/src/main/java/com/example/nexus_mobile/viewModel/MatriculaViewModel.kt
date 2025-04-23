package com.example.nexus_mobile.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.RetrofitClient
import com.example.nexus_mobile.api.CursoApi
import com.example.nexus_mobile.dto.MatriculaCriacaoDto
import com.example.nexus_mobile.utils.UsuarioManager
import kotlinx.coroutines.launch

class MatriculaViewModel (
    application: Application
) : AndroidViewModel(application) {

    var idAssociado by mutableStateOf(0)
    var idCurso by mutableStateOf(0)
    var resultadoMatricula by mutableStateOf<Boolean?>(false)

    private fun getApi(context: Context): CursoApi {
        return RetrofitClient.create(context)
    }

    fun realizarMatricula(context: Context) {
        viewModelScope.launch {
            idAssociado = UsuarioManager.getUserId(context)

            val cursoApi = getApi(context)
                Log.d("MatriculaViewModel", "ID Associado: $idAssociado, ID Curso: $idCurso")
            try {
                val matriculaDto = MatriculaCriacaoDto(idAssociado, idCurso)
                cursoApi.matricular(matriculaDto)
                Log.d("MatriculaViewModel", "Matrícula realizada com sucesso")
                resultadoMatricula = true
            } catch (e: Exception) {
                Log.e("MatriculaViewModel", "Erro ao realizar matrícula: ${e.message}")
                resultadoMatricula = false
            }
        }
    }

    // Verifica se o usuário já está matriculado, caso contrário, realiza a matrícula
    fun verificarMatricula(context: Context) {
        viewModelScope.launch {
            val cursoApi = getApi(context)
            try {
                cursoApi.buscarAssociadoPorCurso(idAssociado, idCurso)
                Log.d("MatriculaViewModel", "Usuário já matriculado no curso $idCurso")
                resultadoMatricula = false
            } catch (e: Exception) {
                Log.w("MatriculaViewModel", "Usuário não matriculado, tentando matricular: ${e.message}")
                realizarMatricula(context)
            }
        }
    }

}