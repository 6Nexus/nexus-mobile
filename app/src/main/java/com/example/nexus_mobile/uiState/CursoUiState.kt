package com.example.nexus_mobile.uiState

import com.example.nexus_mobile.dto.CursoDto

sealed class CursoUiState {
    object Loading : CursoUiState()
    data class Success(val curso: CursoDto) : CursoUiState()
    data class Error(val message: String) : CursoUiState()
}