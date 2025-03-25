package com.example.nexus_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.ui.theme.NexusmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "tela_cadastro") {
                composable("tela_cadastro") {
                    TelaCadastro(navController)
                }
                composable("tela_login") {
                    TelaLogin(navController)
                }

                composable("tela_recuperar_senha") {
                    TelaRecuperarSenha(navController)
                }
            }
        }
    }

    @Preview(
        showBackground = true,
        showSystemUi = true,
        device = Devices.NEXUS_6
    )

    @Preview(showBackground = true)
    @Composable
    fun PreviewTelas() {
        NexusmobileTheme {
            //TelaLogin()
            //TelaCadastro()
            AppBar("Perfil")
        }
    }
}