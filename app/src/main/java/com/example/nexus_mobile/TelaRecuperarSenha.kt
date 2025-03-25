package com.example.nexus_mobile

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
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nexus_mobile.components.AppBar
import com.example.nexus_mobile.ui.theme.cinza
import com.example.nexus_mobile.ui.theme.verdePrincipal


@Composable
fun TelaRecuperarSenha(navController: NavController) {

    var email by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ilustracao_esqueci_senha),
            contentDescription = "Ilustração esqueci senha",
            modifier = Modifier.size(400.dp)
        )

        Text(
            text = "Insira seu email para solicitar redefinição de senha", fontSize = 15.sp,
            color = Color(0xFF313131),
            softWrap = true,
        )

        Spacer(modifier = Modifier.height(40.dp))

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

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { },
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
                text = "Redefinir Senha",
                color = Color.White,
                fontSize = 18.sp,
            )

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Voltar para o",
                color = Color(0xFF004b23),
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Login",
                color = Color(0xFF38b000),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate("tela_login")
                }
            )
        }

    }

}