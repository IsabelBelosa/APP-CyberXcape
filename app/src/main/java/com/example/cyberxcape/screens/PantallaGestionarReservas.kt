package com.example.cyberxcape.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cyberxcape.componentes.MainLayout
import com.example.cyberxcape.model.ViewModel_reservas
import com.example.cyberxcape.ui.theme.Blanco
import com.example.cyberxcape.ui.theme.Negro
import com.example.cyberxcape.ui.theme.Rosa
import android.util.Log

@Composable
fun PantallaGestionarReservas(
    navController: NavHostController,
    viewModel: ViewModel_reservas = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var selectedItem by remember { mutableStateOf("reservas") }

    val reservas by viewModel.reservas.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarReservas()
    }

    MainLayout(
        navController = navController,
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it }
    ) { paddingValues ->

        when {
            cargando -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
                }
            }
            else -> {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (reservas.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No hay reservas registradas", color = Blanco)
                            }
                        }
                    }

                    items(reservas) { reserva ->
                        Log.d("GestionarReservas", "ID de reserva: ${reserva.id}")
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Negro),
                            border = BorderStroke(1.dp, Rosa),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Sala: ${reserva.sala}", color = Rosa)
                                Text("Id de la reserva: ${reserva.id}", color = Blanco)

                                if (!reserva.nombre.isNullOrBlank()) {
                                    Text("Reservado por: ${reserva.nombre}", color = Blanco)
                                }

                                Text(
                                    "Fecha: ${reserva.fecha.ifBlank { "Sin fecha" }}",
                                    color = Blanco
                                )
                                Text("Hora inicio: ${reserva.horaInicio}", color = Blanco)
                                Text("Hora fin: ${reserva.horaFin}", color = Blanco)

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        if (!reserva.id.isNullOrBlank()) {
                                            navController.navigate("verInfo/${reserva.id.trim()}")
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Rosa)
                                ) {
                                    Text("Ver Info", color = Blanco)
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                navController.navigate("formulario")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Rosa)
                        ) {
                            Text("Realizar nueva reserva", color = Blanco)
                        }
                    }
                }
            }
        }
    }
}
