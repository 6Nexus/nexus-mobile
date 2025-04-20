package com.example.nexus_mobile.data.model.matricula

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus_mobile.components.Toast
import com.example.nexus_mobile.components.TipoToast
import com.example.nexus_mobile.data.model.services.AssociadoService
import com.example.nexus_mobile.data.model.services.api
import kotlinx.coroutines.launch

class MatriculaViewModel : ViewModel() {

    var idAssociado by mutableStateOf(0)
    var idCurso by mutableStateOf(0)
    var resultado by mutableStateOf<String?>(null)

    fun realizarMatricula(context: Context) {
        viewModelScope.launch {

            val matriculaRequest = MatriculaCriacaoDto(
                idAssociado = idAssociado,
                idCurso = idCurso
            )

            Log.d(
                "MatriculaViewModel",
                "Realizando matrícula com idAssociado: $idAssociado e idCurso: $idCurso"
            )

            // Cria uma instância do Retrofit
            val associadoService = api.criarApi(context)

            // Faz a chamada de matrícula
            try {
                val response = associadoService.matricular(matriculaRequest)
                Log.d("MatriculaViewModel", "Matrícula realizada com sucesso: $response")

            } catch (e: Exception) {
                Log.e("MatriculaViewModel", "Erro ao realizar matrícula: ${e.message}")
            }

        }

    }

}