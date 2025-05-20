package com.example.cyberxcape.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyberxcape.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class ViewModel_reservas : ViewModel() {
    private val _reservas = MutableStateFlow<List<ReservaResumen>>(emptyList())
    val reservas: StateFlow<List<ReservaResumen>> = _reservas

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _reservaFormulario = MutableStateFlow<ReservaFormulario?>(null)
    val reservaFormulario: StateFlow<ReservaFormulario?> = _reservaFormulario

    fun cargarReservas() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            try {
                val lista = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.obtenerReservas()
                }
                _reservas.value = lista
            } catch (e: Exception) {
                _error.value = "Error al cargar reservas: ${e.localizedMessage}"
            } finally {
                _cargando.value = false
            }
        }
    }


    fun eliminarReserva(reservaId: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.eliminarReserva(reservaId)
                }
                _reservas.value = _reservas.value.filterNot { it.id == reservaId }
            } catch (e: Exception) {
                _error.value = "Error al eliminar: ${e.message}"
            }
        }
    }

    fun actualizarReserva(reserva: ReservaFormulario) {
        viewModelScope.launch {
            try {
                val id = reserva.id ?: run {
                    _error.value = "ID inválido"
                    return@launch
                }

                withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.actualizarReserva(id, reserva)
                }

                val updatedResumen = reserva.toResumen()

                _reservas.value = _reservas.value.map {
                    if (it.id == id) updatedResumen else it
                }

                _error.value = null

            } catch (e: Exception) {
                _error.value = "Error al actualizar: ${e.message}"
            }
        }
    }



    fun cargarReservaPorId(reservaId: String) {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null
            try {
                println("Intentando cargar reserva con ID: $reservaId")
                val reserva = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.obtenerReservaPorId(reservaId)
                }
                println("Reserva cargada desde API: $reserva")
                _reservaFormulario.value = reserva
            } catch (e: Exception) {
                println("Error al cargar reserva: ${e.localizedMessage}")
                _error.value = "Error al cargar la reserva: ${e.localizedMessage}"
            } finally {
                _cargando.value = false
            }
        }
    }
}
