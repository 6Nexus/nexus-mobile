package com.example.nexus_mobile.telas

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nexus_mobile.R
import com.example.nexus_mobile.RetrofitClient
import com.example.nexus_mobile.dto.CadastroRequest
import com.example.nexus_mobile.ui.theme.cinza
import com.example.nexus_mobile.ui.theme.verdePrincipal
import kotlinx.coroutines.launch

@Composable
fun TelaCadastro(navController: NavController) {

    var nome by remember { mutableStateOf("Manoela") }
    var email by remember { mutableStateOf("manoela@gmail.com") }
    var senha by remember { mutableStateOf("123456") }
    var exibirSenha by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isCarregando by remember { mutableStateOf(false) }

    val visualTransformation: VisualTransformation =
        if (exibirSenha) VisualTransformation.None else PasswordVisualTransformation()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
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
                text = "Cadastro", fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF135313),
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome", fontSize = 16.sp) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3a5a40),
                unfocusedBorderColor = Color(0xFFDFDFDF),
                focusedLabelColor = Color(0xFF004b23)
            ),
            modifier = Modifier.size(330.dp, 56.dp),
            shape = RoundedCornerShape(12.dp),


            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Ícone person",
                    modifier = Modifier.size(25.dp),
                    tint = cinza
                )
            }

        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", fontSize = 16.sp) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3a5a40),
                unfocusedBorderColor = Color(0xFFDFDFDF),
                focusedLabelColor = Color(0xFF004b23)
            ),
            modifier = Modifier.size(330.dp, 56.dp),
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

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        val cadastroRequest = CadastroRequest(
                            nome = nome,
                            email = email,
                            senha = senha
                        )

                        val loginApi = RetrofitClient.getLoginApi(context)
                        val response = loginApi.cadastrar(cadastroRequest)

                        if (response.nome.isNotEmpty() && response.email.isNotEmpty()) {
                            Toast.makeText(context, "Cadastro realizado com sucesso! Aguarde aprovação", Toast.LENGTH_LONG).show()
                            navController.navigate("tela_login")
                        } else {
                            Toast.makeText(context, "Erro ao cadastrar. Tente novamente.", Toast.LENGTH_LONG).show()
                            Log.d("TelaCadastro", "Erro ao cadastrar: ${response.email}")
                            Log.d("TelaCadastro", "Erro ao cadastrar: ${response.nome}")
                        }
                        isCarregando = true
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
                        Log.d("TelaCadastro", "Erro ao cadastrar: ${e.message}")
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
            if (isCarregando) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Entrar",
                    color = Color.White,
                    fontSize = 20.sp,
                )

            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Já tem uma conta?",
                color = Color(0xFF004b23),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Entrar",
                color = Color(0xFF38b000),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate("tela_login")
                }
            )
        }


    }

}