package com.example.toolstock.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toolstock.ui.components.EmptyState
import com.example.toolstock.ui.components.EquipoCard
import com.example.toolstock.ui.components.LoadingComponent
import com.example.toolstock.ui.viewmodel.EquipoViewModel

private val Fondo = Color(0xFF111827)
private val Blanco = Color(0xFFF9FAFB)
private val Gris = Color(0xFF9CA3AF)

@Composable
fun EquiposScreen(
    viewModel: EquipoViewModel,
    modifier: Modifier = Modifier
) {
    val equipos by viewModel.equipos.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Fondo)
            .padding(20.dp)
    ) {
        Text(
            text = "Equipos tecnológicos",
            color = Blanco,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inventario registrado en el sistema",
            color = Gris,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        when {
            cargando -> {
                LoadingComponent(
                    mensaje = "Consultando inventario..."
                )
            }

            error != null -> {
                EmptyState(
                    mensaje = "Error al consultar equipos:\n${error}",
                    textoBoton = "Intentar de nuevo",
                    onClick = { viewModel.cargarEquipos() }
                )
            }

            equipos.isEmpty() -> {
                EmptyState(
                    mensaje = "Todavía no hay equipos registrados.",
                    textoBoton = "Actualizar",
                    onClick = { viewModel.cargarEquipos() }
                )
            }

            else -> {
                Text(
                    text = "Total de registros: ${equipos.size}",
                    color = Blanco,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = equipos,
                        key = { equipo -> equipo.id }
                    ) { equipo ->
                        EquipoCard(equipo = equipo)
                    }
                }
            }
        }
    }
}
