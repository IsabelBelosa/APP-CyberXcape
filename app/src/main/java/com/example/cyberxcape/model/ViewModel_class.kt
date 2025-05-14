package com.example.cyberxcape.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.*

class ViewModel_class : ViewModel() {

    private val _uiState = MutableStateFlow(ViewModel_form())
    val uiState: StateFlow<ViewModel_form> = _uiState

    fun onNombreChange(nuevoNombre: String) {
        _uiState.update { it.copy(nombre = nuevoNombre) }
    }

    fun onApellidosChange(nuevosApellidos: String) {
        _uiState.update { it.copy(apellidos = nuevosApellidos) }
    }

    fun onTelefonoChange(nuevoTelefono: String) {
        _uiState.update { it.copy(telefono = nuevoTelefono) }
    }

    fun onNumeroSocioChange(nuevoNumeroSocio: String) {
        _uiState.update { it.copy(numeroSocio = nuevoNumeroSocio) }
    }

    fun onEmailChange(nuevoEmail: String) {
        _uiState.update { it.copy(email = nuevoEmail) }
    }

    fun onNumeroJugadoresChange(nuevoNumeroJugadores: String) {
        _uiState.update { it.copy(numeroJugadores = nuevoNumeroJugadores) }
    }

    fun onSalaChange(sala: String) {
        _uiState.update { it.copy(sala = sala) }
    }

    fun onDificultadChange(dificultad: String) {
        _uiState.update { it.copy(dificultad = dificultad) }
    }

    fun onFechaChange(fecha: Date) {
        _uiState.update { it.copy(fecha = fecha) }
    }

    fun onHoraInicioChange(horaInicio: String) {
        _uiState.update { it.copy(horaInicio = horaInicio) }
    }

    fun onHoraFinChange(horaFin: String) {
        _uiState.update { it.copy(horaFin = horaFin) }
    }

    fun onComentariosChange(comentarios: String) {
        _uiState.update { it.copy(comentarios = comentarios) }
    }

    // Funciones para actualizar la expansión de los dropdowns
    fun onSalaChange(expanded: Boolean) {
        _uiState.update { it.copy(salaExpanded = expanded) }
    }

    fun onDificultadChange(expanded: Boolean) {
        _uiState.update { it.copy(dificultadExpanded = expanded) }
    }

    fun validarYEnviar() {
        val state = _uiState.value

        if (state.nombre.isBlank()) setError("El nombre es obligatorio")
        else if (state.apellidos.isBlank()) setError("Los apellidos son obligatorios")
        else if (state.telefono.isBlank()) setError("El teléfono es obligatorio")
        else if (state.numeroSocio.isBlank()) setError("El número de socio es obligatorio")
        else if (state.email.isBlank()) setError("El email es obligatorio")
        else if (state.numeroJugadores.isBlank()) setError("El número de jugadores es obligatorio")
        else if (state.sala.isBlank()) setError("La sala es obligatoria")
        else if (state.dificultad.isBlank()) setError("La dificultad es obligatoria")
        else if (state.fecha == null) setError("La fecha es obligatoria")
        else if (state.horaInicio.isBlank()) setError("La hora de inicio es obligatoria")
        else if (state.horaFin.isBlank()) setError("La hora de fin es obligatoria")
        else {
            _uiState.update {
                it.copy(errorMensaje = null, envioExitoso = true)
            }
        }
    }

    private fun setError(mensaje: String) {
        _uiState.update { it.copy(errorMensaje = mensaje, envioExitoso = false) }
    }
}
