package com.example.nexus_mobile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.VideoCard
import com.example.nexus_mobile.ui.theme.NexusmobileTheme

@Composable
fun VideosScreen(){
    Column() {
        AppBar("Crypto do Brasil")

        //request
        val requestSimulation = listOf(
            hashMapOf("title" to "Aula 1: Introdução", "url" to "https://www.youtube.com/watch?v=zlr1O4NFLg8"),
            hashMapOf("title" to "Aula 2: Continuação", "url" to "https://www.youtube.com/watch?v=zlr1O4NFLg8"),
            hashMapOf("title" to "Aula 3: Finalização", "url" to "https://www.youtube.com/watch?v=zlr1O4NFLg8")
        )

        requestSimulation.forEach { video ->
            val title = video["title"]!!
            val url = video["url"]!!

            VideoCard(url, title)
        }

    }

    //videoscards
    //questionarycards
}

@Preview(showBackground = true, showSystemUi = true, device =  Devices.PIXEL_2)
@Composable
fun PreviewTelas() {
    NexusmobileTheme {
        //TelaLogin()
        //TelaCadastro()
//        AppBar("Curso de Culinária")
        VideosScreen()
    }
}