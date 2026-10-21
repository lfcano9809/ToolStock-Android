package com.example.toolstock.data.repository

import com.example.toolstock.data.model.Equipo
import com.example.toolstock.data.remote.RetrofitClient

class EquipoRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun obtenerEquipos(): List<Equipo> {
        return apiService.obtenerEquipos()
    }
}