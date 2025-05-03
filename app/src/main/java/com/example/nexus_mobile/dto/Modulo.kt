package com.example.nexus_mobile.dto


data class Modulo(
    val id: Long,
    val titulo: String,
    val descricao: String,
    val ordem: Int,
    val criadoEm: String
)