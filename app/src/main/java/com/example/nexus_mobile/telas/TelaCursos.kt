package com.example.nexus_mobile.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nexus_mobile.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.BarraPesquisa
import com.example.nexus_mobile.components.FiltroCategorias
import com.example.nexus_mobile.components.IlustracaoSemConteudo
import com.example.nexus_mobile.components.ListaCursos
import com.example.nexus_mobile.components.NavigationBar


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
    var query by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }

    var active by remember { mutableStateOf(false) }
    val cursosFiltrados = remember(query, categoriaSelecionada) {
        cursoViewModel.getCursosFiltrados(categoriaSelecionada)
            .filter { it.titulo.contains(query, ignoreCase = true) }
    }


    Column(modifier = Modifier.fillMaxSize()) {

        // BarraPesquisa(query, { query = it })

        BarraPesquisa(
            query = query,
            onQueryChange = { query = it },
            active = active,
            onActiveChange = { active = it },
            onSearch = {
                active = false
            }
        )

        if (cursosFiltrados.isEmpty()) {
            IlustracaoSemConteudo("Nenhum curso encontrado...")
        } else {
            Text(
                text = "Todos os cursos",
                fontSize = 18.sp,
                color = (Color(0xFF013531)),
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
                        favoritosViewModel.alterarFavorito(cursoId)
                    }
                )

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
}

class CursoViewModel : ViewModel() {
    private val cursos = listOf(
        Curso(
            id = 1,
            titulo = "Direitos Humanos",
            categoria = "Educação",
            imagem = R.drawable.curso1,
            modulo = "Módulo 1: Introdução",
            progresso = 40,
            professor = "Dr. João Silva",
            duracao = 5,
            qtdArquivos = 7,
            aulas = listOf(
                "Aula 1: História dos Direitos Humanos",
                "Aula 2: Declaração Universal dos Direitos Humanos",
                "Aula 3: Direitos Fundamentais",
                "Aula 4: Casos Práticos",
                "Prova Final"
            )
        ),
        Curso(
            id = 2,
            titulo = "Informática Básica",
            categoria = "Tecnologia",
            imagem = R.drawable.curso2,
            modulo = "Módulo 2: Pacote Office",
            progresso = 100,
            professor = "Prof. Maria Andrade",
            duracao = 10,
            qtdArquivos = 5,
            aulas = listOf(
                "Aula 1: Introdução ao Computador",
                "Aula 2: Sistema Operacional",
                "Aula 3: Microsoft Word",
                "Aula 4: Microsoft Excel",
                "Aula 5: Microsoft PowerPoint",
                "Prova Final"
            )
        ),
        Curso(
            id = 3,
            titulo = "Saúde Mental",
            categoria = "Saúde",
            imagem = R.drawable.curso3,
            modulo = "Módulo 1: Hábitos para manter o equilíbrio",
            progresso = 0,
            professor = "Dra. Fernanda Costa",
            duracao = 3,
            qtdArquivos = 3,
            aulas = listOf(
                "Aula 1: Introdução à Saúde Mental",
                "Aula 2: Técnicas de Relaxamento",
                "Aula 3: Mindfulness e Bem-estar",
                "Aula 4: Como Lidar com o Estresse",
                "Prova Final"
            )
        )
    )

    // Função para filtrar os cursos com base na categoria
    fun getCursosFiltrados(categoriaSelecionada: String): List<Curso> {
        return if (categoriaSelecionada == "Todos") cursos else cursos.filter { it.categoria == categoriaSelecionada }
    }

    // Função para obter um curso por ID
    fun getCursoById(cursoId: Int): Curso? {
        return cursos.find { it.id == cursoId }
    }

}


data class Curso(
    var id: Int,
    val titulo: String,
    val categoria: String,
    val imagem: Int,
    val modulo: String,
    val progresso: Int,
    val professor: String,
    val duracao: Int,
    val qtdArquivos: Int,
    val aulas: List<String>
)

