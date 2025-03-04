package com.example.nexus_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.nexus_mobile.ui.theme.NexusmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaLogin()
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.NEXUS_6
)

@Composable
fun PreviewTelaLogin() {
    NexusmobileTheme {
        TelaLogin()
    }
}

