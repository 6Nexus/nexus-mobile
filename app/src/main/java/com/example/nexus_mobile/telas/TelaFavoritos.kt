package com.example.nexus_mobile.telas

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexus_mobile.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nexus_mobile.components.BarraPesquisa
import com.example.nexus_mobile.components.FiltroCategorias
import com.example.nexus_mobile.components.ListaCursos
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.viewModel.CursoViewModel
import com.example.nexus_mobile.viewModel.FavoritosViewModel
import com.example.nexus_mobile.viewModel.UsuarioViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun TelaFavoritos(navController: NavController, favoritosViewModel: FavoritosViewModel, usuarioViewModel: UsuarioViewModel) {
    var query by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }
    val cursosFiltrados = favoritosViewModel.cursosFavoritos.filter {
        (categoriaSelecionada == "Todos" || it.categoria == categoriaSelecionada) &&
                (query.isBlank() || it.titulo.contains(query, ignoreCase = true))
    }
    val userId by usuarioViewModel.userId.collectAsState()

    LaunchedEffect(userId) {
        if (userId > 0) {
            favoritosViewModel.carregarFavoritos(idAssociado = userId)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(30.dp))
        BarraPesquisa(query, { query = it })
        Text(
            text = "Todos os cursos favoritos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        FiltroCategorias(categoriaSelecionada) { categoriaSelecionada = it }
        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            ListaCursos(
                navController = navController,
                cursos = cursosFiltrados,
                favoritos = favoritosViewModel.favoritos,
                onFavoritoChanged = { cursoId, _ ->
                    if (userId > 0) {
                        favoritosViewModel.alterarFavorito(idAssociado = userId, cursoId = cursoId)
                    }
                }
            )
        }

        NavigationBar(
            navController = navController,
            telaAtual = "favoritos",
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
    }
}
