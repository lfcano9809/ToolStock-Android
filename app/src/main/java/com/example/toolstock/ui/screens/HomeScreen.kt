package com.example.toolstock.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toolstock.ui.viewmodel.EquipoViewModel

private val Fondo = Color(0xFF111827)
private val Tarjeta = Color(0xFF1F2937)
private val Verde = Color(0xFFA7F36B)
private val Blanco = Color(0xFFF9FAFB)
private val Gris = Color(0xFF9CA3AF)

@Composable
fun HomeScreen(
    viewModel: EquipoViewModel,
    onVerEquipos: () -> Unit,
    onVerPrestamos: () -> Unit,
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
            text = "TOOLSTOCK",
            color = Verde,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Panel principal",
            color = Blanco,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inventario y préstamos de equipos tecnológicos",
            color = Gris
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Tarjeta
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Equipos", color = Gris)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (cargando) "..." else equipos.size.toString(),
                        color = Verde,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Tarjeta
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Préstamos", color = Gris)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Pendiente",
                        color = Verde,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Acciones rápidas",
            color = Blanco,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onVerEquipos,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Verde,
                contentColor = Fondo
            )
        ) {
            Text("Consultar equipos")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onVerPrestamos,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Tarjeta,
                contentColor = Blanco
            )
        ) {
            Text("Consultar préstamos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (error != null) {
            Text(
                text = "No se pudo actualizar el inventario.",
                color = Gris
            )
        } else {
            Text(
                text = "Los datos del inventario provienen del backend.",
                color = Gris,
                fontSize = 13.sp
            )
        }
    }
}
