package com.example.nexus_mobile.dto

data class Video(
    val id: Int,
    val ordem: Int,
    val titulo: String,
    val descricao: String,
    val carregadoNoYoutube: Boolean,
    val youtubeUrl: String?
)