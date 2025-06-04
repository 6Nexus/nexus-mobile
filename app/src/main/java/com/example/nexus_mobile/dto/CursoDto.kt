package com.example.nexus_mobile.dto

data class CursoDto(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val descricao: String,
    val professorId: Int,
    val professorNome: String,
    // capa
    val capaUrl: String
)

data class CapaRespostaDto(
    val capaUrl: String
)





