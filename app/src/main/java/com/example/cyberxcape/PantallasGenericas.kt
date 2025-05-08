package com.example.cyberxcape

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.cyberxcape.ui.theme.*

@Composable
fun PantallaReservas() {
    PantallaGenerica("Gestión de Reservas")
}

@Composable
fun PantallaLocalizacion() {
    PantallaGenerica("Localización")
}

@Composable
fun PantallaGenerica(titulo: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Negro),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = titulo,
            fontSize = 18.sp,
            color = Blanco,
            fontFamily = pressStart2P
        )
    }
}