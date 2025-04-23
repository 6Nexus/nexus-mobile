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

@SuppressLint("UnrememberedMutableState")
@Composable
fun TelaFavoritos(navController: NavController, favoritosViewModel: FavoritosViewModel) {
    val cursoViewModel: CursoViewModel = viewModel()
    var query by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }
    val cursosFiltrados = favoritosViewModel.cursosFavoritos.filter {
        (categoriaSelecionada == "Todos" || it.categoria == categoriaSelecionada) &&
                (query.isBlank() || it.titulo.contains(query, ignoreCase = true))
    }
    var active by remember { mutableStateOf(false) } // usada para barra de pesquisa


    LaunchedEffect(Unit) {
        favoritosViewModel.carregarFavoritos(idAssociado = 2)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(30.dp))
        BarraPesquisa(
            query = query,
            onQueryChange = { query = it },
            active = active,
            onActiveChange = { active = it },
            onSearch = {
                active = false
            }
        )
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
                cursos = cursosFiltrados.map { cursoDto ->
                    Curso(
                        id = 0,
                        titulo = cursoDto.titulo,
                        categoria = cursoDto.categoria,
                        imagem = R.drawable.curso1,
                        modulo = "",
                        professor = cursoDto.professorNome,
                        duracao = 0,
                        qtdArquivos = 0,
                        aulas = emptyList()
                    )
                },
                favoritos = favoritosViewModel.favoritos,
                onFavoritoChanged = { cursoId, _ -> favoritosViewModel.alterarFavorito(idAssociado = 2, cursoId = cursoId) }
            )
        }

        NavigationBar(
            navController = navController,
            telaAtual = "favoritos",
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
    }
}
