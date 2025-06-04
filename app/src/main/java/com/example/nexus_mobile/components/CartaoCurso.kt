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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nexus_mobile.R
import com.example.nexus_mobile.components.Favorito
import com.example.nexus_mobile.dto.CursoDto
import com.example.nexus_mobile.viewModel.CursoViewModel

@Composable
fun CartaoCurso(
    navController: NavController,
    curso: CursoDto,
    favoritos: List<Int>,
    //cursoViewModel: CursoViewModel,
    onFavoritoChanged: (Int, Boolean) -> Unit
) {
    val cursoViewModel : CursoViewModel = viewModel()
    val imagem = R.drawable.curso1


    val capaUrl by cursoViewModel.capaCursoUrl.collectAsState()

    LaunchedEffect(curso.id) {
        cursoViewModel.buscarCapaCurso(curso.id)
    }

    val imagemPainter = if (!curso.capaUrl.isNullOrEmpty()) {
        rememberAsyncImagePainter(model = curso.capaUrl)
    } else {
        painterResource(id = R.drawable.curso1)
    }



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
                painter = imagemPainter,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = curso.titulo,
                        fontSize = 18.sp,
                        color = Color(19, 83, 19),
                        modifier = Modifier.weight(1f)
                    )

                    // Passa o estado dos favoritos para o componente Favorito
                    Favorito(
                        cursoId = curso.id,
                        favoritos = favoritos,
                        onFavoritoChanged = onFavoritoChanged
                    )
                }

                Text(text = curso.descricao, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))

                Spacer(modifier = Modifier.height(5.dp))

                Box(
                    modifier = Modifier
                        .background(Color(217, 217, 217))
                        .padding(horizontal = 13.dp)
                        .height(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = curso.categoria, fontSize = 11.sp, color = Color.Black)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = { navController.navigate("tela_matricula/${curso.id}") },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(76, 173, 76)),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 20.dp)
                    ) {
                        Text(
                            text = "Ver Curso",
                            color = Color.White,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}


