package com.example.nexus_mobile.dto

data class QuestionarioResponse(
    val id: Int,
    val titulo: String,
    val descricao: String,
    val perguntas: List<Pergunta>
)

data class Pergunta(
    val pergunta: String,
    val respostas: List<Resposta>
)

data class Resposta(
    val resposta: String,
    val respostaCerta: Boolean
)

data class ProgressoRequest(
    val pontuacao: Double,
    val matriculaId: Int,
    val questionarioId: Int
)

data class RespostaProgresso(
    val id: Int,
    val pontuacao: Double,
    val dataAtualizacao: String,
    val matriculaId: Int,
    val questionarioId: Int
)