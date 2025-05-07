package com.example.nexus_mobile.telas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.components.PerguntaCard
import com.example.nexus_mobile.dto.ProgressoRequest
import com.example.nexus_mobile.viewModel.CursoViewModel
import kotlinx.coroutines.launch

@Composable
fun TelaQuestionario(
    navController: NavController,
    moduloId: Int,
    idMatricula: Int,
    viewModel: CursoViewModel
) {
    val questionario by viewModel.questionario.collectAsState()
    val erro by viewModel.erro.collectAsState()

    var respostasSelecionadas by remember { mutableStateOf(emptyList<Int>()) }
    var erros by remember { mutableStateOf(emptyList<Boolean>()) }
    var exibirResultado by remember { mutableStateOf(false) }

    LaunchedEffect(moduloId) {
        viewModel.carregarQuestionario(moduloId)
    }

    if (questionario == null) {
        Text("Carregando questionário...")
        return
    }

    val perguntas = questionario!!.perguntas

    // Inicializa seleção
    if (respostasSelecionadas.isEmpty()) {
        respostasSelecionadas = List(perguntas.size) { -1 }
        erros = List(perguntas.size) { false }
    }

    Column {
        AppBar("Questionário")

        perguntas.forEachIndexed { index, pergunta ->
            PerguntaCard(
                numero = index + 1,
                pergunta = pergunta.pergunta,
                respostas = pergunta.respostas.map { it.resposta },
                respostaCorreta = pergunta.respostas.indexOfFirst { it.respostaCerta },
                respostaSelecionada = respostasSelecionadas[index],
                onRespostaSelecionada = { selectedIndex ->
                    respostasSelecionadas = respostasSelecionadas.toMutableList().apply {
                        set(index, selectedIndex)
                    }
                },
                isErrado = erros[index]
            )
        }

        Button(
            onClick = {
                val acertos = perguntas.indices.count { index ->
                    val correta = perguntas[index].respostas.indexOfFirst { it.respostaCerta }
                    respostasSelecionadas[index] == correta
                }

                erros = perguntas.indices.map { index ->
                    val correta = perguntas[index].respostas.indexOfFirst { it.respostaCerta }
                    respostasSelecionadas[index] != correta
                }

                exibirResultado = true

                viewModel.viewModelScope.launch {
                    viewModel.enviarProgresso(
                        ProgressoRequest(
                            idMatricula = idMatricula,
                            idQuestionario = questionario!!.id,
                            acertos = acertos,
                            erros = perguntas.size - acertos
                        )
                    )
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Enviar Questionário")
        }

        if (exibirResultado) {
            Text(
                text = "Você acertou ${perguntas.size - erros.count { it }} de ${perguntas.size} questões.",
                color = if (erros.all { !it }) Color(0xFF4C7031) else Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
