package com.example.cyberxcape.model

data class Sala(
    val nombre: String,
    val bienvenidos: String,
    val descripcion: String,
    val imagen: Int,
    val tabla: List<List<String>>
)