package com.example.nexus_mobile.dto

data class MatriculaRequest(
    val idAssociado: Int,
    val idCurso: Int
)

data class MatriculaVerificacaoResponse(
    val matriculaId: Int?  // Pode ser null ou um ID válido da matrícula
)
