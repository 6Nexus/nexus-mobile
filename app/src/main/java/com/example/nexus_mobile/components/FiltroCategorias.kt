package com.example.nexus_mobile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FiltroCategorias(categoriaSelecionada: String, onCategoriaSelecionada: (String) -> Unit) {
    val categorias = listOf("Todos", "Educação", "Saúde", "Tecnologia", "Engenharia", "Artes", "Ciências", "Negócios")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(categorias) { categoria ->
            Button(
                onClick = { onCategoriaSelecionada(categoria) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (categoriaSelecionada == categoria) Color(76, 173, 76) else Color(230, 230, 230),
                ),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(32.dp)
            ) {
                Text(categoria, fontSize = 12.sp, color = if (categoriaSelecionada == categoria) Color.White else Color.Black)
            }
        }
    }
}