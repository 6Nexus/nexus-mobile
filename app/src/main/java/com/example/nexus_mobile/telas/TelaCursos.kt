package com.example.nexus_mobile.telas

import BarraPesquisa
import android.util.Log
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nexus_mobile.RetrofitClient
import com.example.nexus_mobile.components.FiltroCategorias
import com.example.nexus_mobile.components.ListaCursos
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.ui.theme.NexusmobileTheme
import com.example.nexus_mobile.viewModel.CursoViewModel
import com.example.nexus_mobile.viewModel.FavoritosViewModel
import com.example.nexus_mobile.viewModel.UsuarioViewModel
import kotlinx.coroutines.launch





@Composable
fun TelaCursos(
    navController: NavController,
    favoritosViewModel: FavoritosViewModel,
    cursoViewModel: CursoViewModel,
    usuarioViewModel: UsuarioViewModel
) {
    val id by usuarioViewModel.userId.collectAsState()
    var query by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }
    val cursosFiltrados = cursoViewModel.cursos.filter {
        it.titulo.contains(query, ignoreCase = true)
    }
    var active by remember { mutableStateOf(false) }

    LaunchedEffect(categoriaSelecionada) {
        val idAssociado = id
        if (categoriaSelecionada == "Todos") {
            cursoViewModel.carregarCursos(idAssociado)
        } else {
            cursoViewModel.carregarCursosPorCategoria(idAssociado, categoriaSelecionada)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(30.dp))
        BarraPesquisa(
            query = query,
            onQueryChange = { query = it },
            onSearch = {

            }
        )
        Text(
            text = "Todos os cursos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        FiltroCategorias(categoriaSelecionada) { novaCategoria ->
            categoriaSelecionada = novaCategoria
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            ListaCursos(
                navController = navController,
                cursos = cursosFiltrados,
                favoritos = favoritosViewModel.favoritos,
                onFavoritoChanged = { cursoId, _ ->
                    if (id > 0) {
                        favoritosViewModel.alterarFavorito(idAssociado = id, cursoId = cursoId)
                    }
                }
            )
        }
        NavigationBar(
            navController = navController,
            telaAtual = "tela_curso",
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
    }
}


