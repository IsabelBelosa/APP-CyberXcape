package com.example.cyberxcape.model

//Se importan las herramientas necesarias para usar ViewModel (de arquitectura de Jetpack) y
// StateFlow (de Kotlin Coroutines) para el manejo reactivo del estado, es decir, que la app reaccione automáticamente a los cambios que haya en la UI


// Esta clase representa los datos del formulario y los valores por defecto.
data class ViewModel_form(
    val nombre: String = "",
    val apellidos: String = "",
    val telefono: String = "",
    val socio: String = "",
    val email: String = "",
    val jugadores: String = "",
    val sala: String = "",
    val dificultad: String = "",
    val fecha: String = "",
    val hora: String = "",
    val horaFin: String = "", // Agregamos el campo para la hora de fin
    val comentario: String = "",
    val errorMensaje: String? = null,
    val envioExitoso: Boolean = false
)
