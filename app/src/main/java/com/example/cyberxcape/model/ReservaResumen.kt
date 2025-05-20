package com.example.cyberxcape.model

import java.text.SimpleDateFormat
import java.util.Locale
import com.google.gson.annotations.SerializedName


@kotlinx.serialization.Serializable
data class ReservaResumen(
    @SerializedName("_id")
    val id: String?,
    val nombre: String?,
    val email: String?,
    val comentarios: String?,
    val sala: String,
    val fecha: String,
    val horaInicio: String,
    val horaFin: String
)


fun ReservaFormulario.toResumen(): ReservaResumen {
    val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val fechaStr = this.fecha?.let { formatoFecha.format(it) } ?: ""
    return ReservaResumen(
        id = this.id ?: "",
        nombre = this.nombre,
        email = this.email,
        comentarios = this.comentarios,
        sala = this.sala,
        fecha = fechaStr,
        horaInicio = this.horaInicio,
        horaFin = this.horaFin
    )
}



