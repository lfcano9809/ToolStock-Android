package com.example.toolstock.data.model

import com.google.gson.annotations.SerializedName
data class Prestamo (
    val id: Long,
    val responsable: String,

    @SerializedName("fecha_prestamo")
    val fechaPrestamo:String,
    val cantidad: Int,
    val herramienta: String? = null,
    val categoria: String? = null,

    @SerializedName("herramienta_id")
    val herramientaId: Long? = null
)