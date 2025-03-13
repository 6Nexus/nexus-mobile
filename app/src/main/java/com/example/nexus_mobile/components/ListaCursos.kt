package com.example.nexus_mobile.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.nexus_mobile.telas.Curso

@Composable
fun ListaCursos(
    cursos: List<Curso>,
    favoritos: List<Int>,
    onCursoClick: (Curso) -> Unit,
    onFavoritoChanged: (Int, Boolean) -> Unit
) {
    LazyColumn {
        items(cursos) { curso ->
            CartaoCurso(
                curso = curso,
                favoritos = favoritos,
                onCursoClick = onCursoClick,
                onFavoritoChanged = onFavoritoChanged
            )
        }
    }
}
