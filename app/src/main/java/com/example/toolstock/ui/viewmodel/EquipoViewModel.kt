package com.example.toolstock.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toolstock.data.model.Equipo
import com.example.toolstock.data.repository.EquipoRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EquipoViewModel : ViewModel() {

    private val repository = EquipoRepository()

    private val _equipos = MutableStateFlow<List<Equipo>>(emptyList())
    val equipos: StateFlow<List<Equipo>> = _equipos.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        cargarEquipos()
    }

    fun cargarEquipos() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null

            try {
                _equipos.value = repository.obtenerEquipos()
            } catch (e: Exception) {
                _error.value = e.message ?: "No fue posible cargar los equipos"
            } finally {
                _cargando.value = false
            }
        }
    }
}