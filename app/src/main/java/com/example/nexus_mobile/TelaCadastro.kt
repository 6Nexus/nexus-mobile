package com.example.nexus_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus_mobile.ui.theme.cinza
import com.example.nexus_mobile.ui.theme.verdePrincipal

@Composable
fun TelaCadastro() {


    Column(
        modifier = Modifier.fillMaxSize(),
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
            value = "",
            onValueChange = {},
            label = { Text("Nome", fontSize = 16.sp) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = verdePrincipal,
                unfocusedBorderColor = Color(0xFFDFDFDF)
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
            value = "",
            onValueChange = {},
            label = { Text("Email", fontSize = 16.sp) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = verdePrincipal,
                unfocusedBorderColor = Color(0xFFDFDFDF)
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
            value = "",
            onValueChange = {},
            label = { Text("Senha", fontSize = 16.sp) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = verdePrincipal,
                unfocusedBorderColor = Color(0xFFDFDFDF)
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
                text = "Entrar",
                color = Color.White,
                fontSize = 20.sp,

                )

        }


    }

}