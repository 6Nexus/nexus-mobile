package com.example.nexus_mobile.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nexus_mobile.telas.Curso
import com.example.nexus_mobile.components.Favorito

@Composable
fun CartaoCurso(
    navController: NavController,
    curso: Curso,
    favoritos: List<Int>,
    onFavoritoChanged: (Int, Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(6.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Image(
                painter = painterResource(id = curso.imagem),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = curso.titulo, fontSize = 18.sp, color = Color(19, 83, 19), modifier = Modifier.weight(1f))

                    Favorito(
                        cursoId = curso.id,
                        favoritos = favoritos,
                        onFavoritoChanged = onFavoritoChanged
                    )
                }

                Text(text = curso.modulo, fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))

                Spacer(modifier = Modifier.height(5.dp))

                Box(
                    modifier = Modifier
                        .background(Color(217, 217, 217))
                        .padding(horizontal = 13.dp)
                        .height(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = curso.categoria, fontSize = 9.sp, color = Color.Black)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = { navController.navigate("tela_matricula/${curso.id}") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(76, 173, 76)),
                        modifier = Modifier
                            .padding(top = 26.dp)
                            .height(30.dp)
                            .width(100.dp)
                    ) {
                        Text(
                            text = "Ver Curso",
                            color = Color.White,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    Text(
                        text = if (curso.progresso == 100) "Concluído" else "${curso.progresso}% progresso",
                        fontSize = 12.sp,
                        color = if (curso.progresso == 100) Color(76, 173, 76) else Color.Gray,
                        modifier = Modifier.align(Alignment.CenterVertically).padding(top = 26.dp)
                    )
                }
            }
        }
    }
}

