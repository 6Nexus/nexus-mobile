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
fun TelaMatricula(
    cursoId: Int,
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    cursoViewModel: CursoViewModel
){
    val id by usuarioViewModel.userId.collectAsState()
    val curso by cursoViewModel.curso.collectAsState()
    val modulos by cursoViewModel.modulos.collectAsState()
    val matriculaRealizada by cursoViewModel.matriculaRealizada.collectAsState()
    var iniciado by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!matriculaRealizada) {
            cursoViewModel.verificarMatricula(id, cursoId)
            iniciado = true
        }
    }


    LaunchedEffect(key1 = id, key2 = cursoId) {
        if (id > 0) {
            Log.d("TelaMatricula", "Chamando carregarCursoPorId com id=$id e cursoId=$cursoId")
            cursoViewModel.carregarCursoPorId(id, cursoId)
            Log.d("TelaMatricula", "Chamando verificarMatricula com id=$id e cursoId=$cursoId")
            cursoViewModel.verificarMatricula(id, cursoId)
            cursoViewModel.carregarModulos(cursoId)
        } else {
            Log.w("TelaMatricula", "ID do usuário ainda é inválido: $id")
        }
    }

    if (curso == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        containerColor = Color(0xFFF8F8F8),
        topBar = {
            Column {
                AppBar(descricao = cursoViewModel.curso.toString())
                Spacer(modifier = Modifier.height(30.dp))
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
                                text = "Curso: ${curso?.titulo}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Professor(a): ${curso?.professorNome}",
                                fontSize = 17.sp,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.End
                        ) {
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(
                                onClick = {
                                    cursoViewModel.matricular(id, cursoId)
                                    iniciado = false
                                },
                                enabled = !matriculaRealizada,
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (matriculaRealizada) Color.LightGray else Color.White
                                )
                            ) {
                                Text(
                                    if (matriculaRealizada) "Matriculado" else "Matricule-se",
                                    color = if (matriculaRealizada) Color.DarkGray else Color(0xFF4CAF50)
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
                        .wrapContentHeight()
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
                                progress = 0.7f,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = Color(0xFF4CAF50)
                            )
                        }

                        if (matriculaRealizada) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Log.d("TelaMatricula", "id do modulo ${modulo.id}")
                            Button(
                                onClick = {
                                    iniciado = true
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