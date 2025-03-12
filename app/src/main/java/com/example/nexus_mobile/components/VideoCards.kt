package com.example.nexus_mobile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.nexus_mobile.R
import com.example.nexus_mobile.screens.VideosScreen
import com.example.nexus_mobile.ui.theme.NexusmobileTheme

@Composable
fun VideoCard(
    url: String,
    videoTitle: String,
){
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .aspectRatio(4f),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight()
            ) {
                Image(
//                    painter = rememberAsyncImagePainter("https://img.youtube.com/vi/${extractId(url)}/0.jpg"),
                    painter = painterResource(id = R.drawable.thumbnail_test),
                    contentDescription = "Thumbnail do vídeo",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .weight(2.5f)
                    .padding(16.dp)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = videoTitle,
                    color = Color(0xFF928888)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .border(3.dp, color = Color(0xFF4CAD4C), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Ícone Home",
                        modifier = Modifier.size(25.dp),
                        tint = Color(0xFF4CAD4C)
                    )
                }
            }
        }
    }

}

@Composable
fun QuestionaryCard() {

}

@Composable
fun VideoPlayer() {

}

fun extractId(url:String): String? {
    val regex = "v=([a-zA-Z0-9_-]+)".toRegex()
    val matchResult = regex.find(url)
    return matchResult?.groups?.get(1)?.value
}

@Preview(showBackground = true, showSystemUi = true, device =  Devices.PIXEL_2)
@Composable
fun PreviewTelas() {
    VideoCard("https://www.youtube.com/watch?v=QhM8Unf16zg", "Aula 1: Introdução ao backend")
}