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
    val idMatricula: Int,
    val idQuestionario: Int,
    val acertos: Int,
    val erros: Int
)