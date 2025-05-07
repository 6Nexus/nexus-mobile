package com.example.nexus_mobile.telas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus_mobile.R
import com.example.nexus_mobile.RetrofitClient
import com.example.nexus_mobile.utils.TokenManager
import com.example.nexus_mobile.dto.LoginRequest
import com.example.nexus_mobile.ui.theme.cinza
import com.example.nexus_mobile.ui.theme.verdePrincipal
import com.example.nexus_mobile.utils.UsuarioManager
import com.example.nexus_mobile.viewModel.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(
    navController: NavController,
    context: Context,
    usuarioViewModel: UsuarioViewModel
) {

    var isChecked by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var exibirSenha by remember { mutableStateOf(false) }
    var erroEmail by remember { mutableStateOf("") }
    var erroSenha by remember { mutableStateOf("") }

    val visualTransformation: VisualTransformation =
        if (exibirSenha) VisualTransformation.None else PasswordVisualTransformation()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {


        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo Mães em Luta",
            modifier = Modifier.size(250.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp)
        ) {

            Text(
                text = "Login", fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF135313),
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontSize = 16.sp) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3a5a40),
                unfocusedBorderColor = Color(0xFFDFDFDF),
                focusedLabelColor = Color(0xFF004b23)
            ),
            modifier = Modifier
                .size(330.dp, 56.dp),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = "Ícone email",
                    modifier = Modifier.size(25.dp),
                    tint = cinza
                )
            }
        )

        if (erroEmail.isNotEmpty()) {
            Text(
                text = erroEmail,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 40.dp, top = 4.dp)
                    .align(Alignment.Start)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha", fontSize = 16.sp) },
            visualTransformation = visualTransformation,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3a5a40),
                unfocusedBorderColor = Color(0xFFDFDFDF),
                focusedLabelColor = Color(0xFF004b23)
            ),
            modifier = Modifier.size(330.dp, 56.dp),
            shape = RoundedCornerShape(12.dp),

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Ícone senha",
                    modifier = Modifier.size(25.dp),
                    tint = cinza
                )
            },

            trailingIcon = {
                IconButton(onClick = { exibirSenha = !exibirSenha }) {
                    Icon(
                        imageVector = if (exibirSenha) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "ícone para visibilidade da senha",
                        modifier = Modifier.size(25.dp),
                        tint = cinza
                    )
                }
            }
        )

        if (erroSenha.isNotEmpty()) {
            Text(
                text = erroSenha,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 40.dp, top = 4.dp)
                    .align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        ) {


            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = verdePrincipal,
                    uncheckedColor = cinza
                )

            )

            Text(
                text = "Lembre-me",
                color = Color(0xFF004b23),
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                Log.d("Login", "Botão de login clicado")

                erroEmail = ""
                erroSenha = ""

                if (email.isBlank()) {
                    erroEmail = "O campo de e-mail é obrigatório"
                    return@Button
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    erroEmail = "Formato de e-mail inválido"
                    return@Button
                }

                val loginRequest = LoginRequest(email, senha)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Log.d("Login", "Chamando API de login...")
                        val resposta = RetrofitClient.getLoginApi(context).login(loginRequest)
                        Log.d("Login", "Token recebido: ${resposta.token}")
                        TokenManager.salvarToken(context, resposta.token)

                        withContext(Dispatchers.Main) {
                            usuarioViewModel.setUserData(
                                nome = resposta.nome,
                                email = resposta.email,
                                userId = resposta.userId
                            )
                            navController.navigate("home") {
                                popUpTo("tela_login") { inclusive = true }
                            }
                        }

                    } catch (e: HttpException) {
                        Log.e("Login", "Erro HTTP: ${e.code()} - ${e.message()}")
                        withContext(Dispatchers.Main) {
                            when (e.code()) {
                                404 -> erroEmail = "Email não cadastrado"
                                401 -> erroSenha = "Senha incorreta"
                                else -> erroSenha = "Erro ao fazer login"
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("Login", "Erro inesperado", e)
                        withContext(Dispatchers.Main) {
                            erroSenha = "Erro de conexão. Tente novamente."
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = verdePrincipal,
                contentColor = Color.White,
            ),
            modifier = Modifier
                .size(330.dp, 56.dp)
                .shadow(8.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = "Entrar",
                color = Color.White,
                fontSize = 20.sp,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Não tem uma conta?",
                color = Color(0xFF004b23),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Cadastre-se",
                color = Color(0xFF38b000),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate("tela_cadastro")
                }
            )
        }

        Text(
            text = "Esqueci minha senha",
            color = Color(0xFF004b23),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable {
                navController.navigate("tela_recuperar_senha")
            }
        )
    }
}
