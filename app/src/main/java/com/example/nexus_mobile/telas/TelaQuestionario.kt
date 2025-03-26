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
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.components.PerguntaCard

@Composable
fun TelaQuestionario() {
    var respostasCorretas by remember { mutableStateOf(0) }
    var exibirResultado by remember { mutableStateOf(false) }
    var erros by remember { mutableStateOf(listOf(false, false, false)) }
    var respostasSelecionadas by remember { mutableStateOf(listOf(-1, -1, -1)) }

    val scrollState = rememberScrollState()

    val perguntas = listOf(
        Triple("Lorem Lorem", listOf("Opção 1", "Opção 2", "Opção 3"), 1),
        Triple("Lorem Lorem", listOf("Opção A", "Opção B", "Opção C"), 1),
        Triple("Lorem Lorem", listOf("Resposta X", "Resposta Y", "Resposta Z"), 1)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        AppBar("Finalização: Questionário")

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            perguntas.forEachIndexed { index, (pergunta, respostas, correta) ->
                PerguntaCard(
                    numero = index + 1,
                    pergunta = pergunta,
                    respostas = respostas,
                    respostaCorreta = correta,
                    respostaSelecionada = respostasSelecionadas[index],
                    onRespostaSelecionada = { selectedIndex ->
                        respostasSelecionadas = respostasSelecionadas.toMutableList().apply {
                            set(index, selectedIndex) // Salva o índice da resposta selecionada
                        }
                    },
                    isErrado = erros.getOrElse(index) { false }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    // Calcula as respostas corretas comparando os índices
                    respostasCorretas = respostasSelecionadas.count { selectedIndex ->
                        selectedIndex != -1 && selectedIndex == perguntas[respostasSelecionadas.indexOf(selectedIndex)].third
                    }

                    // Atualiza se os erros devem ser exibidos
                    exibirResultado = true
                    erros = respostasSelecionadas.mapIndexed { index, resposta ->
                        resposta != perguntas[index].third
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C7031))
            ) {
                Text("Enviar Questionário", color = Color.White)
            }

            if (exibirResultado) {
                Text(
                    text = if (respostasCorretas == perguntas.size) "Parabéns! Você acertou 100% das respostas."
                    else "Não atingiu a nota suficiente.",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    color = if (respostasCorretas == perguntas.size) Color(0xFF4C7031) else Color.Red
                )
            }
        }

//        NavigationBar(
//            selecionarTela = {},
//            telaAtual = "questionario",
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
    }
}