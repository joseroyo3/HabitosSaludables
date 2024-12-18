package com.campusdigitalfp.habitossaludables

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campusdigitalfp.habitossaludables.ui.theme.HabitosSaludablesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitosSaludablesTheme {
                VistaListaHabitos(SampleData.habitSample)
            }
        }
    }
}

//@Preview(showBackground = true)
@Preview(showBackground = true, name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewListaHabitos() {
    HabitosSaludablesTheme {
        VistaListaHabitos(SampleData.habitSample)
    }
}

data class Habito(val titulo: String, val descripcion: String)

@Composable
fun VistaHabito(habito: Habito) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.estilo_de_vida),
            contentDescription = "Icono estilo de vida",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)

        )
        Spacer(modifier = Modifier.width(8.dp))

        // Mantenemos un registro de si el mensaje está expandido o no en este
        var isExpanded by remember { mutableStateOf(false) }

        // surfaceColor se actualizará gradualmente de un color a otro
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = "AnimacionDeColor",
        )

        Column (modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = habito.titulo,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp,
                // el color surfaceColor cambiará gradualmente de primario a surface
                color = surfaceColor,
                // animateContentSize cambiará el tamaño de la Superficie gradualmente
                modifier = Modifier.animateContentSize().padding(1.dp)) {
                Text(
                    text = habito.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

@Composable
fun VistaListaHabitos(habitos: List<Habito>) {
    LazyColumn {
        items(habitos) { habito ->
            VistaHabito(habito)
        }
    }
}