
package com.example.toolstock.data.remote

import com.example.toolstock.data.model.Equipo
import com.example.toolstock.data.model.EquipoRequest
import com.example.toolstock.data.model.Prestamo
import com.example.toolstock.data.model.PrestamoRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("api/herramientas")
    suspend fun obtenerEquipos(): List<Equipo>

    @POST("api/herramientas")
    suspend fun registrarEquipo(
        @Body equipo: EquipoRequest
    ): Equipo

    @GET("api/prestamos")
    suspend fun obtenerPrestamos(): List<Prestamo>

    @POST("api/prestamos")
    suspend fun registrarPrestamo(
        @Body prestamo: PrestamoRequest
    ): Prestamo

    @GET("api/prestamos/herramienta/{id}")
    suspend fun obtenerPrestamosPorEquipo(
        @Path("id") id: Long
    ): List<Prestamo>
}
