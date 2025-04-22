package com.example.nexus_mobile.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.nexus_mobile.utils.UsuarioManager
import com.example.nexus_mobile.viewModel.UsuarioViewModel

@Composable
fun BotaoLogout(
    navController: NavController,
    context: Context,
    usuarioViewModel: UsuarioViewModel
) {
    Button(
        onClick = {
            UsuarioManager.limparDados(context)
            usuarioViewModel.limparUsuario()
            navController.navigate("tela_login") {
                popUpTo("home") { inclusive = true }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
            Text("Sair", color = Color.White)
    }
}