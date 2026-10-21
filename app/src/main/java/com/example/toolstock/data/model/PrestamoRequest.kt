
package com.example.toolstock.data.model

import com.google.gson.annotations.SerializedName

data class PrestamoRequest(
    val responsable: String,

    @SerializedName("fecha_prestamo")
    val fechaPrestamo: String,

    val cantidad: Int,

    @SerializedName("herramienta_id")
    val herramientaId: Long
)
