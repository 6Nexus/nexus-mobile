package com.example.nexus_mobile.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexus_mobile.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.nexus_mobile.components.BarraPesquisa
import com.example.nexus_mobile.components.Favorito
import com.example.nexus_mobile.components.FiltroCategorias
import com.example.nexus_mobile.components.ListaCursos
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.ui.theme.NexusmobileTheme


@Composable
fun Home(navController: NavController, favoritosViewModel: FavoritosViewModel) {
    var query by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Todos") }
    var cursoSelecionado by remember { mutableStateOf<Curso?>(null) }

    when {
        cursoSelecionado != null -> {
            TelaMatricula(cursoSelecionado!!) { cursoSelecionado = null }
        }
        else -> {
            Column(modifier = Modifier.fillMaxSize()) {

                Spacer(modifier = Modifier.height(20.dp))
                BarraPesquisa(query, { query = it })

                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .shadow(6.dp, shape = RoundedCornerShape(16.dp))
                        .height(150.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Olá, Maria Eduarda",
                            fontSize = 30.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Continuar assistindo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier.weight(1f)) {
                    ListaCursos(
                        cursos = getCursosFiltrados(categoriaSelecionada),
                        favoritos = favoritosViewModel.favoritos,
                        onCursoClick = { cursoSelecionado = it },
                        onFavoritoChanged = { cursoId, _ -> favoritosViewModel.alterarFavorito(cursoId) }
                    )
                }

                NavigationBar(
                    navController = navController,
                    telaAtual = "home",
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            }
        }
    }
}
