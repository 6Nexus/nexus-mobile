package com.example.nexus_mobile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus_mobile.ui.theme.verdePrincipal

@Composable
fun PerguntaCard(
    numero: Int,
    pergunta: String,
    respostas: List<String>,
    respostaCorreta: Int,
    onRespostaSelecionada: (Int) -> Unit,
    respostaSelecionada: Int,
    isErrado: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // O card de resposta ocupa toda a largura disponível
            .padding(horizontal = 16.dp, vertical = 8.dp) // Ajuste do espaçamento
            .shadow(2.dp, RoundedCornerShape(12.dp)), // Sombra e borda arredondada
        shape = RoundedCornerShape(12.dp), // Bordas mais arredondadas
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F5F6) // Cor do card de resposta
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "$numero. $pergunta",
                fontSize = 16.sp, // Tamanho de fonte menor
                color = Color(0xFF4C7031),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            respostas.forEachIndexed { index, resposta ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth() // O card de resposta ocupa toda a largura disponível
                        .padding(vertical = 4.dp) // Menor espaçamento vertical entre respostas
                        .clickable {
                            onRespostaSelecionada(index)
                        }
                        .shadow(2.dp, shape = RoundedCornerShape(20.dp)), // Sombra apenas nas laterais e embaixo
                    shape = RoundedCornerShape(20.dp), // Bordas arredondadas
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            isErrado && respostaSelecionada == index -> Color(0xFFFFCDD2) // Vermelho para resposta errada
                            !isErrado && respostaSelecionada == index -> Color(0xFFC8E6C9) // Verde para resposta certa
                            else -> Color(0xFFF8F5F6) // Cor padrão para respostas não selecionadas
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp), // Menor padding para reduzir altura
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${'a' + index}) $resposta",
                            fontSize = 14.sp, // Texto menor para as opções de resposta
                            color = Color.Black,
                            modifier = Modifier.weight(1f) // Deixa o texto ocupar o espaço restante
                        )

                        RadioButton(
                            selected = respostaSelecionada == index,
                            onClick = { onRespostaSelecionada(index) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = verdePrincipal
                            )
                        )
                    }
                }
            }
        }
    }
}