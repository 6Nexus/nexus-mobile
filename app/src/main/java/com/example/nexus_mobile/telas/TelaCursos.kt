package com.example.nexus_mobile.telas

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
import com.example.nexus_mobile.components.BarraPesquisa
import com.example.nexus_mobile.components.FiltroCategorias
import com.example.nexus_mobile.components.IlustracaoSemConteudo
import com.example.nexus_mobile.components.ListaCursos
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.ui.theme.NexusmobileTheme
import com.example.nexus_mobile.viewModel.CursoViewModel
import com.example.nexus_mobile.viewModel.FavoritosViewModel
import com.example.nexus_mobile.viewModel.UsuarioViewModel
import kotlinx.coroutines.launch


class FavoritosViewModel : ViewModel() {
    private val _favoritos = mutableStateListOf<Int>()
    val favoritos: List<Int> get() = _favoritos

    fun alterarFavorito(cursoId: Int) {
        if (_favoritos.contains(cursoId)) {
            _favoritos.remove(cursoId)
        } else {
            _favoritos.add(cursoId)
        }
    }
}


@Composable
fun TelaCursos(navController: NavController, favoritosViewModel: FavoritosViewModel) {
    val cursoViewModel: CursoViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val id by usuarioViewModel.id.collectAsState()
    var query by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }
    // val cursosFiltrados = cursoViewModel.cursos

    var active by remember { mutableStateOf(false) }
    val cursosFiltrados = remember(query, categoriaSelecionada) {
        cursoViewModel.getCursosFiltrados(categoriaSelecionada)
            .filter { it.titulo.contains(query, ignoreCase = true) }
    }


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
            active = active,
            onActiveChange = { active = it },
            onSearch = {
                active = false
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
            if (query.isNotBlank() && cursosFiltrados.isEmpty()) {
                IlustracaoSemConteudo("Nenhum curso encontrado...")
            } else {
                ListaCursos(
                    navController = navController,
                    cursos = cursosFiltrados.map { cursoDto ->
                        Curso(
                            id = cursoDto.id,
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
                    onFavoritoChanged = { cursoId, _ ->
                        favoritosViewModel.alterarFavorito(
                            idAssociado = 2,
                            cursoId = cursoId
                        )
                    }
                )
            }
        }

        NavigationBar(
            navController = navController,
            telaAtual = "tela_curso",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}


data class Curso(
    var id: Int,
    val titulo: String,
    val categoria: String,
    val imagem: Int,
    val modulo: String,
    val professor: String,
    val duracao: Int,
    val qtdArquivos: Int,
    val aulas: List<String>
)

