
package com.example.toolstock.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.toolstock.data.model.EquipoRequest
import com.example.toolstock.data.remote.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun AgregarEquipoScreen(
    onVolver: () -> Unit = {},
    onEquipoRegistrado: () -> Unit = {}
) {
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var guardando by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Agregar equipo",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFA7F36B)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del equipo") },
            placeholder = { Text("Ej. Taladro eléctrico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !guardando
        )

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría") },
            placeholder = { Text("Ej. Eléctrica") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !guardando
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = {
                cantidad = it.filter(Char::isDigit)
            },
            label = { Text("Cantidad disponible") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !guardando
        )

        if (mensaje.isNotBlank()) {
            Text(
                text = mensaje,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                if (guardando) return@Button

                val nombreLimpio = nombre.trim()
                val categoriaLimpia = categoria.trim()
                val cantidadInt = cantidad.toIntOrNull()

                if (
                    nombreLimpio.isBlank() ||
                    categoriaLimpia.isBlank() ||
                    cantidadInt == null ||
                    cantidadInt <= 0
                ) {
                    mensaje = "Completa todos los campos con datos válidos. La cantidad debe ser mayor que cero."
                    return@Button
                }

                scope.launch {
                    guardando = true
                    mensaje = ""

                    try {
                        RetrofitClient.apiService.registrarEquipo(
                            EquipoRequest(
                                nombre = nombreLimpio,
                                categoria = categoriaLimpia,
                                cantidad = cantidadInt
                            )
                        )

                        mensaje = "Equipo registrado correctamente."
                        nombre = ""
                        categoria = ""
                        cantidad = ""

                        onEquipoRegistrado()
                    } catch (e: Exception) {
                        mensaje = "No se pudo registrar el equipo: ${
                            e.localizedMessage ?: "Error desconocido"
                        }"
                    } finally {
                        guardando = false
                    }
                }
            },
            enabled = !guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (guardando) {
                CircularProgressIndicator()
            } else {
                Text("Registrar equipo")
            }
        }

        Button(
            onClick = onVolver,
            enabled = !guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
