package com.example.nexus_mobile.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.nexus_mobile.telas.Curso

@Composable
fun ListaCursos(
    navController: NavController,
    cursos: List<Curso>,
    favoritos: List<Int>,
    onFavoritoChanged: (Int, Boolean) -> Unit
) {
    LazyColumn {
        items(cursos) { curso ->
            CartaoCurso(
                navController = navController,
                curso = curso,
                favoritos = favoritos,
                onFavoritoChanged = onFavoritoChanged
            )
        }
    }
}
