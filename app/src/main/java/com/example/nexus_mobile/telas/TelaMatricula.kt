package com.example.nexus_mobile.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus_mobile.R
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.ui.theme.NexusmobileTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaMatricula(curso: Curso, onVoltar: () -> Unit) {
    var telaAtual by remember { mutableStateOf("cursos") }

    Box(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
            },
            navigationIcon = {
                IconButton(onClick = onVoltar) {
                    Image(
                        painter = painterResource(id = R.drawable.voltar),
                        contentDescription = "Voltar",
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF4CAF50)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 100.dp)
        ) {
            Image(
                painter = painterResource(id = curso.imagem),
                contentDescription = "Curso",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                curso.titulo,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Text("Professor: ${curso.professor}", fontSize = 16.sp, color = Color(36, 80, 36))
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(curso.modulo, fontSize = 15.sp, color = Color(82, 78, 78, 190))
                    Text("Duração total: ${curso.duracao}h", fontSize = 15.sp, color = Color(82, 78, 78, 190))
                    Text("Arquivos: ${curso.qtdArquivos} Arquivos", fontSize = 15.sp, color = Color(82, 78, 78, 190))
                }
                Button(
                    onClick = { /* Lógica de matrícula */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Matricular", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(6.dp))
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(250, 250, 250))
                    .padding(8.dp)
            ) {
                val scrollState = rememberScrollState()

                Column {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "Conteúdo do curso",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp),
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        curso.aulas.forEach { aula ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .height(50.dp)
                                    .shadow(5.dp, shape = RoundedCornerShape(6.dp))
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(250, 250, 250))
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(aula, modifier = Modifier.weight(1f), fontSize = 15.sp, color = Color(82, 78, 78, 190))
                                    Text("5m", color = Color(82, 78, 78, 190), fontSize = 15.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
        NavigationBar(
            selecionarTela = { telaAtual = it },
            telaAtual = telaAtual,
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTelaMatricula() {
    NexusmobileTheme {
        TelaMatricula(
            curso = Curso(
                id = 1,
                titulo = "Curso de Kotlin",
                categoria = "Programação",
                imagem = R.drawable.curso2,
                modulo = "Módulo 1: Introdução",
                progresso = 50,
                professor = "Gisele Vieira",
                duracao = 1,
                qtdArquivos = 7,
                aulas = listOf(
                    "Aula 1: Introdução",
                    "Aula 2: Variáveis e Tipos",
                    "Aula 3: Estruturas de Controle",
                    "Aula 4: Funções e Classes",
                    "Aula 5: Funções e Classes",
                    "Aula 6: Funções e Classes",
                    "Aula 7: Funções e Classes"
                )
            ),
            onVoltar = {}
        )
    }
}