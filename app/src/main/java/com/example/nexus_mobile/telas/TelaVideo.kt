package com.example.nexus_mobile.telas

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.example.nexus_mobile.R
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.components.QuestionaryCard
import com.example.nexus_mobile.components.VideoCard
import com.example.nexus_mobile.components.VideoPlayer
import com.example.nexus_mobile.components.videoMenu
import com.example.nexus_mobile.viewModel.CursoViewModel
import com.example.nexus_mobile.viewModel.UsuarioViewModel

@OptIn(UnstableApi::class)
@Composable
fun TelaVideo(
    navController: NavController,
    moduleTitle: String,
    cursoViewModel: CursoViewModel,
    moduloId: Int,
    cursoId: Int,
    usuarioViewModel: UsuarioViewModel
) {
    val viewModel: CursoViewModel = viewModel()
    val idMatricula by cursoViewModel.idMatricula.collectAsState()
    val videos by cursoViewModel.videos.collectAsState()
    val loading by cursoViewModel.loading.collectAsState()
    val erro by cursoViewModel.erro.collectAsState()
    val idAssociado by usuarioViewModel.userId.collectAsState()

    var currentVideo by remember { mutableStateOf("") }
    var showPlayer by remember { mutableStateOf(false) }

    // Garantimos que a matrícula só será verificada quando o idAssociado estiver disponível
    LaunchedEffect(moduloId, idAssociado) {
        if (idAssociado > 0) {
            cursoViewModel.carregarVideos(moduloId)
//            viewModel.verificarMatricula(idAssociado, cursoId)
        }
    }

    Scaffold(
        topBar = {
            AppBar(descricao = moduleTitle)
        },
        bottomBar = {
            NavigationBar(
                navController = navController,
                telaAtual = "tela_curso",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Ação do WhatsApp */ },
                containerColor = Color(76, 173, 76)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.whatsapp),
                    contentDescription = "WhatsApp",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    ) { valoresDePadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(valoresDePadding)
        ) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (erro != null) {
                Text(
                    text = erro ?: "",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                // Caso o usuário ainda não esteja matriculado
                if ((idMatricula ?: 0) <= 0) {
                    Text(
                        text = "Você precisa se matricular para assistir aos vídeos.",
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                }

                if (showPlayer) {
                    videoMenu({ showPlayer = false })
                    key(currentVideo) {
                        VideoPlayer(currentVideo)
                    }
                }

                videos.forEach { video ->
                    val url = video.youtubeUrl ?: ""
                    if (url.isNotEmpty()) {
                        VideoCard(
                            url = url,
                            videoTitle = video.titulo,
                            onClick = { videoUrl, shouldShow ->
                                currentVideo = videoUrl
                                showPlayer = shouldShow
                            }
                        )
                    }
                }
            }

            Log.d("TelaVideo", "id da matricula $idMatricula id modulo $moduloId")

            // Exibe o QuestionaryCard quando idMatricula não é null
            idMatricula?.let { matriculaId ->
                QuestionaryCard(
                    navController = navController,
                    moduloId = moduloId,
                    idMatricula = matriculaId
                )
            }
        }
    }
}