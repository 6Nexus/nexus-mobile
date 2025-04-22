package com.example.nexus_mobile.telas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nexus_mobile.R
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.components.NavigationBar
import com.example.nexus_mobile.data.model.perfil.PerfilViewModel

@Composable
fun TelaPerfil(
    navController: NavController,
    viewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current
    val nome by remember { derivedStateOf { viewModel.nome } }
    val email by remember { derivedStateOf { viewModel.email } }
    val senha by remember { derivedStateOf { viewModel.senha } }
    val isCarregando by remember { derivedStateOf { viewModel.isCarregando } }
    val errorMessage by remember { derivedStateOf { viewModel.errorMessage } }

    LaunchedEffect(Unit) {
        viewModel.carregarDadosUsuario(context)
    }

    Scaffold(
        topBar = { AppBar(descricao = "Perfil") },
        bottomBar = {
            NavigationBar(navController = navController, telaAtual = "tela_perfil")
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

            // Nome
            Text("Nome Completo", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = nome,
                onValueChange = { viewModel.nome = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite seu nome") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email
            Text("Email", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite seu email") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Senha
            Text("Senha", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = senha,
                onValueChange = { viewModel.senha = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Digite sua senha") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (isCarregando) {
                CircularProgressIndicator()
            }

            if (errorMessage != null) {
                Text(text = errorMessage ?: "", color = Color.Red)
            }

            // Botões
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        viewModel.atualizarPerfil(context)
                        Toast.makeText(context, "Dados atualizados!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAD4C)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Salvar", color = Color.White)
                }

                Button(
                    onClick = { /* Lógica de logout aqui */ },
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
