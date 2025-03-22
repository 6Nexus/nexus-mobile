package com.example.nexus_mobile.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import com.example.nexus_mobile.R

@Composable
fun NavigationBar(
    selecionarTela: (String) -> Unit,
    telaAtual: String,
    modifier: Modifier = Modifier
) {
    val botoes = remember {
        listOf(
            "home" to R.drawable.home,
            "cursos" to R.drawable.school,
            "perfil" to R.drawable.perfil,
            "favoritos" to R.drawable.favoritos,
        )
    }

    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = Color(76, 173, 76),
        tonalElevation = 10.dp
    ) {
        botoes.forEach { (nomeTela, icone) ->
            NavigationBarItem(
                nomeTela = nomeTela,
                icone = icone,
                isSelecionado = telaAtual == nomeTela,
                selecionarTela = selecionarTela
            )
        }
    }
}

@Composable
fun RowScope.NavigationBarItem(
    nomeTela: String,
    icone: Int,
    isSelecionado: Boolean,
    selecionarTela: (String) -> Unit
) {
    IconButton(
        onClick = { selecionarTela(nomeTela) },
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelecionado) Color(0xFF388E3C) else Color.Transparent)
    ) {
        Icon(
            painter = painterResource(id = icone),
            contentDescription = nomeTela,
            tint = Color.White
        )
    }
}