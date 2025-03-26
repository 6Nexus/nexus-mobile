package com.example.nexus_mobile.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.QuestionaryCard
import com.example.nexus_mobile.components.VideoCard
import com.example.nexus_mobile.components.VideoPlayer
import com.example.nexus_mobile.components.videoMenu

@Composable
fun TelaVideo(
    moduleTitle: String
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppBar(moduleTitle)

        //player
        var currentVideo by remember { mutableStateOf("") }
        var showPlayer by remember { mutableStateOf(false) }

        if (showPlayer) {
            videoMenu({ showPlayer = false })
            key(currentVideo) {
                VideoPlayer(
                    currentVideo
                )
            }
        }

        //fake request
        val requestSimulation = listOf(
            hashMapOf("title" to "Aula 1: Introdução", "url" to "https://www.youtube.com/watch?v=QKviWFOfcog"),
            hashMapOf("title" to "Aula 2: Continuação", "url" to "https://www.youtube.com/watch?v=v-vRNPXMEGU&t=89s"),
            hashMapOf("title" to "Aula 3: Finalização", "url" to "https://www.youtube.com/watch?v=7XsLu-CHQnQ&t=1907s")
        )

        //video click callback
        val onVideoCardClick: (String, Boolean) -> Unit = { videoId, shouldShow ->
            currentVideo = videoId
            showPlayer = shouldShow
        }

        //video cards
        requestSimulation.forEach { video ->
            val title = video["title"]!!
            val url = video["url"]!!

            VideoCard(url, title, onVideoCardClick)
        }

        //questionary card
        QuestionaryCard()

    }
    //nav
}