package com.example.cyberxcape.model

import java.util.*

data class ReservaFormulario(
    val id: String? = null,
    val nombre: String,
    val apellidos: String,
    val telefono: String,
    val numeroSocio: String,
    val email: String,
    val numeroJugadores: Int,
    val sala: String,
    val dificultad: String,
    val fecha: Date?,
    val horaInicio: String,
    val horaFin: String,
    val comentarios: String
)
