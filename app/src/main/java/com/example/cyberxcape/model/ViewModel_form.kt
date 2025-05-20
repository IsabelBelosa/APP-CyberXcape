package com.example.cyberxcape.model

import java.util.*

data class ViewModel_form(
    val nombre: String = "",
    val apellidos: String = "",
    val telefono: String = "",
    val numeroSocio: String = "",
    val email: String = "",
    val numeroJugadores: String = "",
    val sala: String = "",
    val dificultad: String = "",
    val fecha: Date? = null,
    val horaInicio: String = "",
    val horaFin: String = "",
    val comentarios: String = "",
    val errorMensaje: String? = null,
    val envioExitoso: Boolean = false,
    val salaExpanded: Boolean = false, // Nueva propiedad
    val dificultadExpanded: Boolean = false // Nueva propiedad
)

fun ViewModel_form.toReservaFormulario(): ReservaFormulario {
    return ReservaFormulario(
        nombre = nombre,
        apellidos = apellidos,
        telefono = telefono,
        numeroSocio = numeroSocio,
        email = email,
        numeroJugadores = numeroJugadores.toIntOrNull() ?: 0,
        sala = sala,
        dificultad = dificultad,
        fecha = fecha,
        horaInicio = horaInicio,
        horaFin = horaFin,
        comentarios = comentarios
    )
}



