package com.example.nexus_mobile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus_mobile.telas.Curso

@Composable
fun DetalhesCurso(curso: Curso, onVoltar: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        IconButton(onClick = { onVoltar() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
        }
        Text(
            text = curso.titulo,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Módulos do Curso", fontSize = 18.sp, color = Color.Gray)

        val modulos = listOf("Introdução", "Aula 1", "Aula 2", "Conclusão")
        LazyColumn {
            items(modulos) { modulo ->
                Text(
                    text = modulo,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}