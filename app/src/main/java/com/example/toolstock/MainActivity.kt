package com.example.toolstock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toolstock.ui.screens.AgregarEquipoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToolStockApp()
                }
            }
        }
    }
}

@Composable
fun ToolStockApp() {
    var pantallaActual by rememberSaveable { mutableStateOf("inicio") }

    when (pantallaActual) {
        "inicio" -> {
            PantallaInicio(
                onAgregarEquipo = {
                    pantallaActual = "agregar_equipo"
                },
                onRegistrarPrestamo = {
                    pantallaActual = "prestamos"
                }
            )
        }

        "agregar_equipo" -> {
            AgregarEquipoScreen(
                onVolver = {
                    pantallaActual = "inicio"
                },
                onEquipoRegistrado = {
                    // Al registrar, permanecemos en el formulario.
                }
            )
        }

        "prestamos" -> {
            PantallaPendiente(
                titulo = "Préstamos",
                mensaje = "Aquí conectaremos el registro y la consulta de préstamos.",
                onVolver = {
                    pantallaActual = "inicio"
                }
            )
        }
    }
}

@Composable
fun PantallaInicio(
    onAgregarEquipo: () -> Unit,
    onRegistrarPrestamo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ToolStock",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Gestión de herramientas",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onAgregarEquipo,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar equipo")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onRegistrarPrestamo,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Préstamos")
        }
    }
}

@Composable
fun PantallaPendiente(
    titulo: String,
    mensaje: String,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = mensaje)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
