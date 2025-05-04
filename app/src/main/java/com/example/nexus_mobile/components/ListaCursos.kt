package com.example.nexus_mobile.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.nexus_mobile.dto.CursoDto

@Composable
fun ListaCursos(
    navController: NavController,
    cursos: List<CursoDto>, // mudou aqui
    favoritos: List<Int>,
    onFavoritoChanged: (Int, Boolean) -> Unit
) {
    LazyColumn {
        items(cursos) { cursoDto ->
            CartaoCurso(
                navController = navController,
                curso = cursoDto, // passa o CursoDto direto
                favoritos = favoritos,
                onFavoritoChanged = onFavoritoChanged
            )
        }
    }
}
