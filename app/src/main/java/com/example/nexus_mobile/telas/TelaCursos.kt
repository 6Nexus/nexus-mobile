package com.example.nexus_mobile.telas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexus_mobile.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.nexus_mobile.components.BarraPesquisa
import com.example.nexus_mobile.components.FiltroCategorias
import com.example.nexus_mobile.components.ListaCursos
import com.example.nexus_mobile.components.DetalhesCurso
import com.example.nexus_mobile.components.NavigationBar


@Composable
fun TelaCursos() {
    var query by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }
    var cursoSelecionado by remember { mutableStateOf<Curso?>(null) }
    var telaAtual by remember { mutableStateOf("cursos") }
    val favoritos = remember { mutableStateListOf<Int>() }
    val context = LocalContext.current

    val onFavoritoChanged: (Int, Boolean) -> Unit = { cursoId, isFavorited ->
        if (isFavorited) {
            if (!favoritos.contains(cursoId)) favoritos.add(cursoId)
        } else {
            favoritos.remove(cursoId)
        }
    }

    when {
        cursoSelecionado != null -> {
            DetalhesCurso(cursoSelecionado!!) { cursoSelecionado = null }
        }
        telaAtual == "favoritos" -> {
            val cursosFavoritos = getCursosFiltrados(categoriaSelecionada).filter { it.id in favoritos }
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
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
                    ListaCursos(
                        cursos = cursosFavoritos,
                        favoritos = favoritos,
                        onCursoClick = { cursoSelecionado = it },
                        onFavoritoChanged = onFavoritoChanged
                    )
                }
                NavigationBar(
                    selecionarTela = { telaAtual = it },
                    telaAtual = telaAtual,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.height(50.dp))
                    BarraPesquisa(query, { query = it })
                    Text(
                        text = "Todos os cursos",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    FiltroCategorias(categoriaSelecionada) { categoriaSelecionada = it }
                    Spacer(modifier = Modifier.height(16.dp))

                    ListaCursos(
                        cursos = getCursosFiltrados(categoriaSelecionada),
                        favoritos = favoritos,
                        onCursoClick = { cursoSelecionado = it },
                        onFavoritoChanged = onFavoritoChanged
                    )
                }
                NavigationBar(
                    selecionarTela = { telaAtual = it },
                    telaAtual = telaAtual,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}



fun getCursosFiltrados(categoriaSelecionada: String): List<Curso> {
    val cursos = listOf(
        Curso(1,"Direitos Humanos", "Educação", R.drawable.curso1, "Módulo 1: Introdução", 40),
        Curso(2, "Informática Básica", "Tecnologia", R.drawable.curso2, "Módulo 2: Pacote Office", 100),
        Curso(3, "Saúde Mental", "Saúde", R.drawable.curso3, "Módulo 1: Hábitos para manter o equilíbrio", 0)
    )
    return if (categoriaSelecionada == "Todos") cursos else cursos.filter { it.categoria == categoriaSelecionada }
}


data class Curso(var id: Int, val titulo: String, val categoria: String, val imagem: Int, val modulo: String, val progresso: Int)





