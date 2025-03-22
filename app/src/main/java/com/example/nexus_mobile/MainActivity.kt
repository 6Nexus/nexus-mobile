package com.example.nexus_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.nexus_mobile.telas.TelaPerfil
import com.example.nexus_mobile.ui.theme.NexusmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaPerfil(selecionarTela = {},
                       telaAtual = "perfil")
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
        TelaPerfil(selecionarTela = {},
                   telaAtual = "perfil")
    }
}
