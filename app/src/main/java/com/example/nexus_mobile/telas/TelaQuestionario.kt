package com.example.nexus_mobile.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.components.PerguntaCard
import com.example.nexus_mobile.dto.ProgressoRequest
import com.example.nexus_mobile.ui.theme.verdePrincipal
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
    val progresso by viewModel.progressoResponse.collectAsState()
    val erro by viewModel.erro.collectAsState()

    var respostasSelecionadas by remember { mutableStateOf(emptyList<Int>()) }
    var erros by remember { mutableStateOf(emptyList<Boolean>()) }
    var exibirResultado by remember { mutableStateOf(false) }

    LaunchedEffect(moduloId) {
        viewModel.carregarQuestionario(moduloId)
        viewModel.buscarProgressoQuestionario(idMatricula, moduloId)
    }

    if (progresso != null) {
        Column(Modifier.padding(16.dp)) {
            Text("Você já respondeu este questionário.")
            Text("Pontuação: ${progresso!!.pontuacao.toInt()}%", color = Color(0xFF4C7031))
        }
        return
    }

    if (questionario == null) {
        Text("Carregando questionário...")
        return
    }

    val perguntas = questionario!!.perguntas

    if (respostasSelecionadas.isEmpty()) {
        respostasSelecionadas = List(perguntas.size) { -1 }
        erros = List(perguntas.size) { false }
    }

    Column {
        AppBar("Questionário")
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                .clickable{ navController.popBackStack() }
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_back),
//                contentDescription = "Voltar",
//                modifier = Modifier.size(24.dp)
//            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Voltar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )
        }

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
                if (respostasSelecionadas.any { it == -1 }) {
                    erros = perguntas.indices.map { index ->
                        respostasSelecionadas[index] == -1
                    }
                    return@Button
                }

                val acertos = perguntas.indices.count { index ->
                    val correta = perguntas[index].respostas.indexOfFirst { it.respostaCerta }
                    respostasSelecionadas[index] == correta
                }

                val pontuacao = (acertos.toDouble() / perguntas.size) * 100.0

                erros = perguntas.indices.map { index ->
                    val correta = perguntas[index].respostas.indexOfFirst { it.respostaCerta }
                    respostasSelecionadas[index] != correta
                }

                exibirResultado = true

                questionario?.let {
                    viewModel.enviarProgressoQuestionario(
                        pontuacao = pontuacao,
                        matriculaId = idMatricula,
                        questionarioId = it.id
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = verdePrincipal,
                contentColor = Color.White,
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .size(330.dp, 56.dp)
            ,
            shape = RoundedCornerShape(10.dp),
            enabled = !exibirResultado
        ) {
            Text("Enviar Questionário"
                , fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
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

