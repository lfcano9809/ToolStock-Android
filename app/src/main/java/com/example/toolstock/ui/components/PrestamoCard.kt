
package com.example.toolstock.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.toolstock.data.model.Prestamo

@Composable
fun PrestamoCard(
    prestamo: Prestamo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1F2937)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = prestamo.herramienta ?: "Herramienta no especificada",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFA7F36B)
            )

            Text(
                text = "Responsable: ${prestamo.responsable}",
                color = Color(0xFFF9FAFB)
            )

            Text(
                text = "Categoría: ${prestamo.categoria ?: "Sin categoría"}",
                color = Color(0xFF9CA3AF)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fecha: ${prestamo.fechaPrestamo}",
                    color = Color(0xFF9CA3AF)
                )

                Text(
                    text = "Cantidad: ${prestamo.cantidad}",
                    color = Color(0xFFF9FAFB),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
