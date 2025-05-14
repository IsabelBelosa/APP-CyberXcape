package com.example.cyberxcape.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Esta clase manejará el estado del formulario.
class ViewModel_class : ViewModel() {
    // Estado que mantiene los datos del formulario.
    private val _uiState = MutableStateFlow(ViewModel_form())
    val uiState: StateFlow<ViewModel_form> = _uiState

    // Funciones que actualizan el estado del formulario con cada cambio.

    fun onNombreChange(nombre: String) {
        _uiState.value = _uiState.value.copy(nombre = nombre)
    }

    fun onApellidosChange(apellidos: String) {
        _uiState.value = _uiState.value.copy(apellidos = apellidos)
    }

    fun onTelefonoChange(telefono: String) {
        _uiState.value = _uiState.value.copy(telefono = telefono)
    }

    fun onSocioChange(socio: String) {
        _uiState.value = _uiState.value.copy(socio = socio)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onJugadoresChange(jugadores: String) {
        _uiState.value = _uiState.value.copy(jugadores = jugadores)
    }

    fun onSalaChange(sala: String) {
        _uiState.value = _uiState.value.copy(sala = sala)
    }

    fun onDificultadChange(dificultad: String) {
        _uiState.value = _uiState.value.copy(dificultad = dificultad)
    }

    fun onFechaChange(fecha: String) {
        _uiState.value = _uiState.value.copy(fecha = fecha)
    }

    fun onHoraInicioChange(hora: String) {
        _uiState.value = _uiState.value.copy(hora = hora)
    }

    fun onHoraFinChange(horaFin: String) {
        _uiState.value = _uiState.value.copy(horaFin = horaFin) // Actualizamos horaFin
    }

    fun onComentarioChange(comentario: String) {
        _uiState.value = _uiState.value.copy(comentario = comentario)
    }

    // Función que valida los datos y los envía

    fun validarYEnviar() {
        val state = _uiState.value
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

        when {
            state.nombre.isBlank() -> setError("El nombre es obligatorio")
            state.apellidos.isBlank() -> setError("Los apellidos son obligatorios")
            state.socio.length < 8 -> setError("El número de socio debe tener al menos 8 caracteres")
            !emailRegex.matches(state.email) -> setError("El email no tiene un formato válido")
            else -> {
                _uiState.update {
                    it.copy(errorMensaje = null, envioExitoso = true)
                }
            }

        }
    }

    private fun setError(mensaje: String) {
        _uiState.update { it.copy(errorMensaje = mensaje, envioExitoso = false) }
    }

}

// Tenemos una UI reactiva: cuando el usuario escribe algo,
// la función correspondiente se llama y actualiza el estado.

// Gracias a StateFlow, la UI que observe uiState se volverá a componer
// automáticamente con los nuevos datos.