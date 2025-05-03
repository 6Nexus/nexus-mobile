package com.example.nexus_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.telas.Curso
import com.example.nexus_mobile.telas.Home
import com.example.nexus_mobile.telas.TelaCadastro
import com.example.nexus_mobile.telas.TelaCursos
import com.example.nexus_mobile.telas.TelaFavoritos
import com.example.nexus_mobile.telas.TelaInicial
import com.example.nexus_mobile.telas.TelaLogin
import com.example.nexus_mobile.telas.TelaMatricula
import com.example.nexus_mobile.telas.TelaPerfil
import com.example.nexus_mobile.telas.TelaQuestionario
import com.example.nexus_mobile.telas.TelaRecuperarSenha
import com.example.nexus_mobile.telas.TelaVideo
import com.example.nexus_mobile.ui.theme.NexusmobileTheme
import com.example.nexus_mobile.uiState.CursoUiState
import com.example.nexus_mobile.viewModel.CursoViewModel
import com.example.nexus_mobile.viewModel.FavoritosViewModel
import com.example.nexus_mobile.viewModel.UsuarioViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val favoritosViewModel: FavoritosViewModel = viewModel()
            val usuarioViewModel: UsuarioViewModel = viewModel()
            val contexto = LocalContext.current

            NavHost(navController = navController, startDestination = "tela_cadastro") {

                composable("tela_cadastro") {
                    TelaCadastro(navController)
                }

                composable("tela_login") {
                    val context = LocalContext.current
                    TelaLogin(navController = navController, context = context)
                }

                composable("tela_recuperar_senha") {
                    TelaRecuperarSenha(navController)
                }

                composable("home") { Home(navController, usuarioViewModel, contexto, favoritosViewModel) }

                composable("tela_curso") { TelaCursos(navController, favoritosViewModel) }

                composable("tela_perfil") { TelaPerfil(navController) }

                composable("favoritos") { TelaFavoritos(navController, favoritosViewModel, usuarioViewModel) }

                composable(
                    route = "tela_matricula/{cursoId}",
                    arguments = listOf(navArgument("cursoId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val cursoId = backStackEntry.arguments?.getInt("cursoId") ?: 0
                    TelaMatricula(cursoId = cursoId, navController = navController)
                }

                composable(
                    route = "videos/{moduloId}",
                    arguments = listOf(navArgument("moduloId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val moduloId = backStackEntry.arguments?.getInt("moduloId") ?: 0
                    TelaVideo(
                        navController = navController,
                        moduleTitle = "Título do Módulo",
                        moduloId = moduloId
                    )
                }

                composable("questionario") { TelaQuestionario(navController) }

            }
        }
    }
}