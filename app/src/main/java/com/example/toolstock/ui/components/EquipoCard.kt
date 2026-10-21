package com.example.toolstock.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toolstock.data.model.Equipo

private val FondoTarjeta = Color(0xFF1F2937)
private val Verde = Color(0xFFA7F36B)
private val Blanco = Color(0xFFF9FAFB)
private val Gris = Color(0xFF9CA3AF)

@Composable
fun EquipoCard(
    equipo: Equipo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = FondoTarjeta
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = equipo.nombre,
                color = Blanco,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Categoría: ${equipo.categoria}",
                color = Gris,
                fontSize = 14.sp
            )

            Row {
                Text(
                    text = "Cantidad registrada",
                    color = Gris,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = equipo.cantidad.toString(),
                    color = Verde,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
