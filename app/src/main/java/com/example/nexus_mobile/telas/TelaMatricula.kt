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
import androidx.navigation.NavController
import com.example.nexus_mobile.R
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.ui.theme.NexusmobileTheme
import androidx.compose.material3.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nexus_mobile.uiState.CursoUiState
import com.example.nexus_mobile.viewModel.CursoViewModel
import com.example.nexus_mobile.viewModel.UsuarioViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.platform.LocalContext
import com.example.nexus_mobile.components.TipoToast
import com.example.nexus_mobile.components.Toast
import com.example.nexus_mobile.data.model.matricula.MatriculaViewModel
import kotlinx.coroutines.delay

@Composable
fun TelaMatricula(cursoId: Int, navController: NavController) {
    val cursoViewModel: CursoViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val id by usuarioViewModel.id.collectAsState()
    val uiState = cursoViewModel.uiState.value

    val matriculaViewModel: MatriculaViewModel = viewModel()
    val context = LocalContext.current
    val resultado = matriculaViewModel.resultadoMatricula

    LaunchedEffect(cursoId) {
        cursoViewModel.carregarCursoPorId(id, cursoId)
    }

    when (uiState) {
        is CursoUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        is CursoUiState.SuccessCurso -> {
            val curso = uiState.curso
            Scaffold(
                topBar = { AppBar(descricao = curso.titulo) },
                bottomBar = {
                    NavigationBar(
                        navController = navController,
                        telaAtual = "tela_curso",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            ) { valoresDePadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(top = 130.dp)
                ) {
                    // Imagem fixa do curso
                    Image(
                        painter = painterResource(id = R.drawable.curso1), // Use uma imagem fixa
                        contentDescription = "Curso",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
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
                    Text(
                        "Professor: ${curso.professorNome}",
                        fontSize = 16.sp,
                        color = Color(36, 80, 36)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(curso.descricao, fontSize = 15.sp, color = Color(82, 78, 78, 190))
                            Text(
                                "Duração total: 5h",
                                fontSize = 15.sp,
                                color = Color(82, 78, 78, 190)
                            ) // Valor fixo
                            Text(
                                "Arquivos: 7 Arquivos",
                                fontSize = 15.sp,
                                color = Color(82, 78, 78, 190)
                            ) // Valor fixo
                        }
                        Button(
                            onClick = {
                                matriculaViewModel.idAssociado = 16
                                matriculaViewModel.idCurso = cursoId
                                matriculaViewModel.verificarMatricula(context)
                            },
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
                            .shadow(4.dp, shape = RoundedCornerShape(6.dp))
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(248, 245, 245, 255))
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
                                // Valores fixos de aulas
                                listOf("Aula 1", "Aula 2", "Aula 3").forEach { aula ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .height(50.dp)
                                            .shadow(2.dp, shape = RoundedCornerShape(6.dp))
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color(248, 245, 245, 255))
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                aula,
                                                modifier = Modifier.weight(1f),
                                                fontSize = 15.sp,
                                                color = Color(82, 78, 78, 190)
                                            )
                                            Text(
                                                "5m",
                                                color = Color(82, 78, 78, 190),
                                                fontSize = 15.sp
                                            ) // Duração fixa
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // VALIDAÇÕES PARA CHAMADA DE TOAST
                    if (resultado == true) {
                        LaunchedEffect(resultado) {
                            delay(3000)
                            matriculaViewModel.resultadoMatricula = true
                            navController.navigate("video")
                        }
                        Toast(
                            mensagem = "Matrícula realizada com sucesso!",
                            tipoToast = TipoToast.Sucesso,
                            modifier = Modifier
                                .padding(top = 100.dp)
                                .align(alignment = Alignment.TopCenter as Alignment.Horizontal)
                        )
                    }
                }
            }
        }

        is CursoUiState.Error -> {
            Text(text = uiState.message, color = Color.Red, modifier = Modifier.fillMaxSize())
        }

        is CursoUiState.Success -> TODO()
    }
}


