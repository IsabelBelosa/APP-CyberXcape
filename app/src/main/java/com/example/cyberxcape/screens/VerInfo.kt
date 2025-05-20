package com.example.cyberxcape.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cyberxcape.componentes.MainLayout
import com.example.cyberxcape.model.ViewModel_reservas
import com.example.cyberxcape.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext



@Composable
fun VerInfo(
    reservaId: String,
    navController: NavHostController,
    viewModel: ViewModel_reservas = viewModel()
) {
    val reserva by viewModel.reservaFormulario.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    LaunchedEffect(reservaId) {
        viewModel.cargarReservaPorId(reservaId)
    }

    if (cargando) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (reserva == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Reserva no encontrada", color = MaterialTheme.colorScheme.error)
        }
        return
    }

    val reservaNoNull = reserva!!

    MainLayout(
        navController = navController,
        selectedItem = "reservas",
        onItemSelected = {}
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Detalles de la Reserva", style = MaterialTheme.typography.titleLarge, color = Rosa)

            Text("Nombre: ${reservaNoNull.nombre}", color = Blanco)
            Text("Apellidos: ${reservaNoNull.apellidos}", color = Blanco)
            Text("Teléfono: ${reservaNoNull.telefono}", color = Blanco)
            Text("Número de Socio: ${reservaNoNull.numeroSocio}", color = Blanco)
            Text("Email: ${reservaNoNull.email}", color = Blanco)
            Text("Número de Jugadores: ${reservaNoNull.numeroJugadores}", color = Blanco)
            Text("Sala: ${reservaNoNull.sala}", color = Blanco)
            Text("Dificultad: ${reservaNoNull.dificultad}", color = Blanco)
            Text("Fecha: ${reservaNoNull.fecha}", color = Blanco)
            Text("Hora Inicio: ${reservaNoNull.horaInicio}", color = Blanco)
            Text("Hora Fin: ${reservaNoNull.horaFin}", color = Blanco)
            Text("Comentarios: ${reservaNoNull.comentarios}", color = Blanco)

            if (error != null) {
                Text(text = error!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { navController.navigate("modificarInfo/${reservaNoNull.id}") },
                    colors = ButtonDefaults.buttonColors(containerColor = Rosa),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Actualizar reserva", color = Blanco,textAlign =TextAlign.Center)
                }}

                Button(
                    onClick = {
                        scope.launch {
                            reservaNoNull.id?.trim()?.let {
                                viewModel.eliminarReserva(it)
                            }
                            Toast.makeText(context, "Reserva eliminada correctamente", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar reserva", color = Blanco)
                }
            }

            Button(
                onClick = { navController.navigate("reservas") },
                colors = ButtonDefaults.buttonColors(containerColor = Rosa),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al listado", color = Blanco)
            }
        }
    }
}