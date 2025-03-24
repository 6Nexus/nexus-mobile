package com.example.nexus_mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.VideoPlayer
import com.example.nexus_mobile.components.VideoPlayerTest
import com.example.nexus_mobile.screens.VideosScreen
import com.example.nexus_mobile.ui.theme.NexusmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideosScreen("Teste de título")
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