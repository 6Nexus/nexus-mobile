package com.example.nexus_mobile.telas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nexus_mobile.R
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.NavigationBar

@Composable
fun TelaPerfil( navController: NavController
) {
    var telaAtual by remember { mutableStateOf("perfil") }

    Scaffold(
        topBar = {
            AppBar(descricao = "Perfil")
        },
        bottomBar = {
            NavigationBar(
                navController = navController,
                telaAtual = "tela_perfil",
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Ação do WhatsApp */ },
                containerColor = Color(76, 173, 76)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.whatsapp),
                    contentDescription = "WhatsApp",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    ) { valoresDePadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(valoresDePadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mg),
                    contentDescription = "Imagem de Perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(BorderStroke(2.dp, Color(76, 173, 76)), CircleShape),
                    contentScale = ContentScale.Crop
                )

                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Editar Foto",
                    tint = Color.White,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(76, 173, 76))
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CampoDeInformacaoEditavel("Maria Eduarda")
            CampoDeInformacaoEditavel("maria.guarda@esptech.school")
            CampoDeInformacaoEditavel("11951288322")

            Spacer(modifier = Modifier.height(24.dp))

            CartaoDeOpcao("Configurações")
            CartaoDeOpcao("Documentos")
        }
    }
}

@Composable
fun CampoDeInformacaoEditavel(info: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = info, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar",
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun CartaoDeOpcao(titulo: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .shadow(8.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F5F5)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(titulo)
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

