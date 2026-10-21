
package com.example.toolstock.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.toolstock.data.model.PrestamoRequest
import com.example.toolstock.data.remote.RetrofitClient
import com.example.toolstock.ui.viewmodel.EquipoViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch

@Composable
fun RegistrarPrestamoScreen(
    equipoViewModel: EquipoViewModel,
    onPrestamoRegistrado: () -> Unit,
    modifier: Modifier = Modifier
) {
    val equipos by equipoViewModel.equipos.collectAsState()
    val scope = rememberCoroutineScope()

    var responsable by remember { mutableStateOf("") }
    var fecha by remember {
        mutableStateOf(
            SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        )
    }
    var cantidad by remember { mutableStateOf("1") }
    var equipoSeleccionadoId by remember { mutableStateOf<Long?>(null) }
    var mensaje by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }

    val equipoSeleccionado = equipos.firstOrNull {
        it.id == equipoSeleccionadoId
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Registrar préstamo",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = responsable,
            onValueChange = { responsable = it },
            label = { Text("Nombre del responsable") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !cargando
        )

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (AAAA-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !cargando
        )

        Text(
            text = "Selecciona una herramienta:",
            style = MaterialTheme.typography.titleMedium
        )

        if (equipos.isEmpty()) {
            Text("No hay herramientas disponibles. Actualiza la lista de herramientas.")
        } else {
            equipos.forEach { equipo ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(equipo.nombre)
                        Text(
                            text = "Categoría: ${equipo.categoria} | Disponible: ${equipo.cantidad}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            equipoSeleccionadoId = equipo.id
                            mensaje = "Seleccionaste: ${equipo.nombre}"
                        },
                        enabled = !cargando
                    ) {
                        Text(
                            if (equipoSeleccionadoId == equipo.id) {
                                "Seleccionada"
                            } else {
                                "Elegir"
                            }
                        )
                    }
                }
            }
        }

        if (equipoSeleccionado != null) {
            Text(
                text = "Herramienta elegida: ${equipoSeleccionado.nombre}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !cargando
        )

        if (mensaje.isNotBlank()) {
            Text(
                text = mensaje,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = {
                if (cargando) return@Button

                scope.launch {
                    mensaje = ""

                    val nombreLimpio = responsable.trim()
                    val cantidadInt = cantidad.toIntOrNull()

                    if (nombreLimpio.isBlank()) {
                        mensaje = "Escribe el nombre del responsable."
                        return@launch
                    }

                    if (equipoSeleccionado == null) {
                        mensaje = "Selecciona una herramienta."
                        return@launch
                    }

                    val formatoFecha = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.US
                    ).apply {
                        isLenient = false
                    }

                    val fechaValida = try {
                        val fechaConvertida = formatoFecha.parse(fecha)
                        fechaConvertida != null &&
                                formatoFecha.format(fechaConvertida) == fecha
                    } catch (e: ParseException) {
                        false
                    }

                    if (!fechaValida) {
                        mensaje = "La fecha debe ser válida y tener el formato AAAA-MM-DD."
                        return@launch
                    }

                    if (cantidadInt == null || cantidadInt <= 0) {
                        mensaje = "La cantidad debe ser un número mayor que cero."
                        return@launch
                    }

                    if (cantidadInt > equipoSeleccionado.cantidad) {
                        mensaje = "No hay suficientes unidades disponibles."
                        return@launch
                    }

                    cargando = true

                    try {
                        RetrofitClient.apiService.registrarPrestamo(
                            PrestamoRequest(
                                responsable = nombreLimpio,
                                fechaPrestamo = fecha,
                                cantidad = cantidadInt,
                                herramientaId = equipoSeleccionado.id
                            )
                        )

                        mensaje = "Préstamo registrado correctamente."
                        responsable = ""
                        cantidad = "1"
                        equipoSeleccionadoId = null

                        equipoViewModel.cargarEquipos()
                        onPrestamoRegistrado()
                    } catch (e: Exception) {
                        mensaje = "No se pudo registrar el préstamo: ${
                            e.message ?: "Error de conexión"
                        }"
                    } finally {
                        cargando = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !cargando
        ) {
            if (cargando) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(4.dp))
                Text("Registrando...")
            } else {
                Text("Registrar préstamo")
            }
        }
    }
}
