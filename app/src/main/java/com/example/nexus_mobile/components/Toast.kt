package com.example.nexus_mobile.components

import android.R.attr.shape
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import com.example.nexus_mobile.ui.theme.amareloAviso
import com.example.nexus_mobile.ui.theme.azulInfo
import com.example.nexus_mobile.ui.theme.branco
import com.example.nexus_mobile.ui.theme.preto
import com.example.nexus_mobile.ui.theme.verdeSucesso
import com.example.nexus_mobile.ui.theme.vermelhoErro
import kotlinx.coroutines.delay

// Componente para exibir mensagems de toast
@Composable
fun Toast(
    mensagem: String,
    tipoToast: TipoToast,
    modifier: Modifier = Modifier,
) {

    val backgroundColor = when (tipoToast) {
        TipoToast.Sucesso -> verdeSucesso
        TipoToast.Erro -> vermelhoErro
        TipoToast.Info -> azulInfo
        TipoToast.Aviso -> amareloAviso
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(1.dp, branco, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = when (tipoToast) {
                TipoToast.Sucesso -> Icons.Rounded.CheckCircle
                TipoToast.Erro -> Icons.Rounded.Error
                TipoToast.Info -> Icons.Rounded.Info
                TipoToast.Aviso -> Icons.Rounded.Warning
            },
            contentDescription = null,
            tint = branco,
        )
        Text(
            text = mensagem,
            color = branco,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )
    }

}