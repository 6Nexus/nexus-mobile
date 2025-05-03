package com.example.nexus_mobile.telas

import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import com.example.nexus_mobile.utils.UsuarioManager

@OptIn(UnstableApi::class)
@Composable
fun TelaMatricula(cursoId: Int, navController: NavController) {
    val cursoViewModel: CursoViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val id by usuarioViewModel.userId.collectAsState()
    val uiState = cursoViewModel.uiState.value
    var matriculado by remember { mutableStateOf(false) }
    val modulos by cursoViewModel.modulos.collectAsState()
    var iniciado by remember { mutableStateOf(false) }

    LaunchedEffect(cursoId, id) {
        if (id > 0) {
            cursoViewModel.carregarCursoPorId(id, cursoId)
            cursoViewModel.carregarModulos(cursoId)
        }
    }

    when (uiState) {
        is CursoUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CursoUiState.SuccessCurso -> {
            val curso = uiState.curso

            Scaffold(
                containerColor = Color(0xFFF8F8F8),
                topBar = {
                    Column {
                        AppBar(descricao = "Perfil")
                        Spacer(modifier = Modifier.width(20.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                                .clickable { navController.popBackStack() }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.seta),
                                contentDescription = "Voltar",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Voltar",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }
                },
                bottomBar = {
                    NavigationBar(
                        navController = navController,
                        telaAtual = "tela_curso",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    item {
                        // Cabeçalho do curso
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "Curso: ${curso.titulo}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Professor(a): ${curso.professorNome}",
                                        fontSize = 17.sp,
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Spacer(modifier = Modifier.height(32.dp)) // Ajuste este valor conforme necessário
                                    Button(
                                        onClick = {
                                            cursoViewModel.matricular(id, cursoId)
                                            matriculado = true
                                        },
                                        enabled = !matriculado,
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (matriculado) Color.LightGray else Color.White
                                        )
                                    ) {
                                        Text(
                                            if (matriculado) "Matriculado" else "Matricule-se",
                                            color = if (matriculado) Color.DarkGray else Color(0xFF4CAF50)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Módulos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B5E20)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(modulos) { modulo ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = modulo.titulo,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = modulo.descricao,
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.widthIn(max = 250.dp)
                                )

                                if (iniciado) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    LinearProgressIndicator(
                                        progress = 0.7f, // Exemplo de progresso
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(6.dp)
                                            .clip(RoundedCornerShape(4.dp)),
                                        color = Color(0xFF4CAF50)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                if (matriculado) {
                                    Button(
                                        onClick = { iniciado = true
                                            navController.navigate("videos/${modulo.id}")
                                                  },
                                        shape = RoundedCornerShape(20.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Text(
                                            text = if (iniciado) "Continuar" else "Começar",
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        is CursoUiState.MatriculaSuccess -> {
            LaunchedEffect(Unit) {
                navController.navigate("video") {
                    popUpTo("tela_matricula/$cursoId") { inclusive = true }
                }
            }
        }

        is CursoUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.message, color = Color.Red)
            }
        }

        is CursoUiState.Success -> { /* Ignorado aqui */ }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun TelaMatriculaPreview() {
//    NexusmobileTheme {
//        val modulo1Iniciado = remember { mutableStateOf(false) }
//        val modulo2Iniciado = remember { mutableStateOf(false) }
//        val matriculado = remember { mutableStateOf(false) } // Novo estado
//
//        Scaffold(
//            containerColor = Color(0xFFF8F8F8),
//            topBar = {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.seta),
//                        contentDescription = "Voltar",
//                        modifier = Modifier.size(24.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "Voltar",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF1B5E20)
//                    )
//                }
//            },
//            bottomBar = {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color(0xFFE8F5E9))
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    Text("Início", color = Color(0xFF4CAF50))
//                    Text("Cursos", color = Color.Gray)
//                    Text("Perfil", color = Color.Gray)
//                }
//            }
//        ) { padding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(padding)
//                    .padding(16.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(160.dp),
//                    shape = RoundedCornerShape(16.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column(
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            Text(
//                                text = "Curso: Introdução ao Kotlin",
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.White
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                text = "Professor(a): Maria Silva",
//                                fontSize = 15.sp,
//                                color = Color.White
//                            )
//                        }
//
//                        Button(
//                            onClick = { matriculado.value = true },
//                            enabled = !matriculado.value,
//                            shape = RoundedCornerShape(10.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = if (matriculado.value) Color.LightGray else Color.White
//                            )
//                        ) {
//                            Text(
//                                if (matriculado.value) "Matriculado" else "Matricule-se",
//                                color = if (matriculado.value) Color.DarkGray else Color(0xFF4CAF50)
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(40.dp))
//                Text(
//                    text = "Módulos",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF1B5E20)
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Módulo 1
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    shape = RoundedCornerShape(16.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color.White),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
//                        Text("Módulo 1", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            "Conteúdo introdutório sobre o tema do módulo...",
//                            fontSize = 12.sp,
//                            color = Color.Gray,
//                            modifier = Modifier.widthIn(max = 250.dp) // Limita a largura do texto
//                        )
//                        if (modulo1Iniciado.value) {
//                            Spacer(modifier = Modifier.height(8.dp))
//                            LinearProgressIndicator(
//                                progress = 0.7f,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(6.dp)
//                                    .clip(RoundedCornerShape(4.dp)),
//                                color = Color(0xFF4CAF50)
//                            )
//                        }
//                        Spacer(modifier = Modifier.height(8.dp))
//                        if (matriculado.value) {
//                            Button(
//                                onClick = { modulo1Iniciado.value = true },
//                                shape = RoundedCornerShape(20.dp),
//                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//                                modifier = Modifier.align(Alignment.End)
//                            ) {
//                                Text(
//                                    if (modulo1Iniciado.value) "Continuar" else "Começar",
//                                    color = Color.White
//                                )
//                            }
//                        }
//                    }
//                }
//
//                // Módulo 2
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp),
//                    shape = RoundedCornerShape(16.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color.White),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text("Módulo 2", fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            "Conteúdo introdutório sobre o tema do módulo...",
//                            fontSize = 12.sp,
//                            color = Color.Gray
//                        )
//                        if (modulo2Iniciado.value) {
//                            Spacer(modifier = Modifier.height(8.dp))
//                            LinearProgressIndicator(
//                                progress = 0.0f,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(6.dp)
//                                    .clip(RoundedCornerShape(4.dp)),
//                                color = Color(0xFF4CAF50)
//                            )
//                        }
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Button(
//                            onClick = { modulo2Iniciado.value = true },
//                            shape = RoundedCornerShape(20.dp),
//                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//                            modifier = Modifier.align(Alignment.End)
//                        ) {
//                            Text(
//                                if (modulo2Iniciado.value) "Continuar" else "Começar",
//                                color = Color.White
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
