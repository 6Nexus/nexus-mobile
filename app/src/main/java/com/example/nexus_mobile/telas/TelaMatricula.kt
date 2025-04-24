package com.example.nexus_mobile.telas

import android.widget.Toast
import androidx.annotation.OptIn
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
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.nexus_mobile.utils.UsuarioManager
import com.example.nexus_mobile.viewModel.MatriculaViewModel

@OptIn(UnstableApi::class)
@Composable
fun TelaMatricula(cursoId: Int, navController: NavController) {
    val cursoViewModel: CursoViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    val userId by usuarioViewModel.userId.collectAsState()

    val uiState = cursoViewModel.uiState.value

    val matriculaViewModel: MatriculaViewModel = viewModel()
    val matriculado by matriculaViewModel.matriculaRealizada.collectAsState()

    LaunchedEffect(matriculado) {
        if (matriculado) {
            navController.navigate("video") {
                popUpTo("tela_matricula/$cursoId") { inclusive = true }
            }
            matriculaViewModel.resetarStatus()
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Curso: ${curso.titulo}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Professor: ${curso.professorNome}", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        matriculaViewModel.matricular(usuarioId = userId, cursoId = cursoId)
                    }
                ) {
                    Text("Matricular")
                }
            }
        }

        is CursoUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.message, color = Color.Red)
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aguardando carregamento...")
            }
        }
    }
}