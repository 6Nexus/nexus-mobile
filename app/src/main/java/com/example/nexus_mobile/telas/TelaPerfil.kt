package com.example.nexus_mobile.telas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nexus_mobile.R
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.NavigationBar

@Composable
fun TelaPerfil(navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppBar(descricao = "Perfil")
        },
        bottomBar = {
            NavigationBar(
                navController = navController,
                telaAtual = "tela_perfil"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Ação do WhatsApp */ },
                containerColor = Color(0xFF4CAD4C)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.whatsapp),
                    contentDescription = "WhatsApp",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Informações Pessoais",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAD4C),
                modifier = Modifier.padding(bottom = 24.dp)
            )


            Text(
                text = "Nome Completo",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite seu nome") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Email",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite seu email") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Senha",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite sua senha") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botões Salvar e Sair da Conta
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botão Salvar
                Button(
                    onClick = { /* Ação de salvar */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAD4C)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Salvar", color = Color.White)
                }

                // Botão Sair da Conta
                Button(
                    onClick = { /* Ação de sair da conta */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Sair de conta", color = Color.White)
                }
            }
        }
    }
}
