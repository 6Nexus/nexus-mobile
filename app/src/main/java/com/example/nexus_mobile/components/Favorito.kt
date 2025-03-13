package com.example.nexus_mobile.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Favorito(
    cursoId: Int,
    favoritos: List<Int>,
    onFavoritoChanged: (Int, Boolean) -> Unit
) {
    val isFavorited = cursoId in favoritos // Verifica diretamente na lista global

    Icon(
        imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = "Favoritar",
        tint = Color(76, 173, 76),
        modifier = Modifier.clickable {
            onFavoritoChanged(cursoId, !isFavorited) // Inverte o estado e chama a função
        }
    )
}

