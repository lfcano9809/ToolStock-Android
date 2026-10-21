
package com.example.toolstock.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.toolstock.data.model.Prestamo
import com.example.toolstock.data.remote.RetrofitClient
import com.example.toolstock.ui.components.PrestamoCard
import kotlinx.coroutines.launch

@Composable
fun PrestamosScreen(
    onRegistrarPrestamo: () -> Unit
) {
    var prestamos by remember {
        mutableStateOf<List<Prestamo>>(emptyList())
    }

    var cargando by remember {
        mutableStateOf(true)
    }

    var mensaje by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    fun cargarPrestamos() {
        scope.launch {
            cargando = true
            mensaje = ""

            try {
                prestamos = RetrofitClient.apiService.obtenerPrestamos()
            } catch (e: Exception) {
                mensaje = "No se pudieron cargar los préstamos: ${
                    e.localizedMessage ?: "error de conexión"
                }"
            } finally {
                cargando = false
            }
        }
    }

    LaunchedEffect(Unit) {
        cargarPrestamos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Préstamos",
            color = Color(0xFFA7F36B),
            style = MaterialTheme.typography.headlineSmall
        )

        Button(
            onClick = onRegistrarPrestamo,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar préstamo")
        }

        Button(
            onClick = { cargarPrestamos() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar lista")
        }

        when {
            cargando -> {
                CircularProgressIndicator()
            }

            mensaje.isNotBlank() -> {
                Text(
                    text = mensaje,
                    color = Color(0xFFFF8A80)
                )
            }

            prestamos.isEmpty() -> {
                Text(
                    text = "Todavía no hay préstamos registrados.",
                    color = Color(0xFF9CA3AF)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = prestamos,
                        key = { it.id }
                    ) { prestamo ->
                        PrestamoCard(prestamo = prestamo)
                    }
                }
            }
        }
    }
}
