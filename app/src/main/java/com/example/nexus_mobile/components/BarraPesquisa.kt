import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.graphics.Shape


@Composable
fun BarraPesquisa(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(bottom = 8.dp)
        .padding(top = 25.dp)
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, Color(0xFFDFDFDF)),
                shape = RoundedCornerShape(16.dp)
            )
        ,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFDFDFDF),
            unfocusedBorderColor = Color(0xFFDFDFDF),
            // focusedLabelColor = Color(0xFF004b23),
            unfocusedContainerColor = Color(240, 240, 240),
            focusedContainerColor = Color(240, 240, 240)
        ),
        placeholder = { Text("Pesquisar...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Limpar busca",
                    modifier = Modifier.clickable {
                        onQueryChange("")
                    }
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        textStyle = TextStyle(fontSize = 16.sp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
            }
        )
    )
}



