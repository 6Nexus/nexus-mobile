package com.example.nexus_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.nexus_mobile.telas.CursoViewModel
import com.example.nexus_mobile.telas.FavoritosViewModel
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
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val favoritosViewModel: FavoritosViewModel = viewModel()

            NavHost(navController = navController, startDestination = "splash_screen") {
                composable("splash_screen") {
                    SplashScreen(navController)
                }

                composable("tela_cadastro") {
                    TelaCadastro(navController)
                }
                composable("tela_login") {
                    TelaLogin(navController)
                }

                composable("tela_recuperar_senha") {
                    TelaRecuperarSenha(navController)
                }

                composable("home") { Home(navController, favoritosViewModel) }

                composable("tela_curso") { TelaCursos(navController, favoritosViewModel) }

                composable("tela_perfil") { TelaPerfil(navController) }

                composable("favoritos") { TelaFavoritos(navController, favoritosViewModel) }

                composable("tela_matricula/{cursoId}") { backStackEntry ->
                    val cursoId = backStackEntry.arguments?.getString("cursoId")?.toInt()
                    val cursoViewModel: CursoViewModel = viewModel()
                    val curso = cursoId?.let { cursoViewModel.getCursoById(it) }

                    if (curso != null) {
                        TelaMatricula(curso, navController)
                    } else {
                        Text("Curso não encontrado")
                    }
                }

                composable("video") { TelaVideo(navController, "Aulas") }

                composable("questionario") { TelaQuestionario(navController) }

            }
        }
    }

    @Preview(
        showBackground = true,
        showSystemUi = true,
        device = Devices.NEXUS_6
    )
    @Composable
    fun PreviewTelas() {
        NexusmobileTheme {
            //TelaLogin()
            //TelaCadastro()
//            AppBar("Perfil")
            val navController = rememberNavController()
            TelaPerfil(navController = navController)
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("tela_cadastro")
    }

    TelaInicial(navController = navController)
}