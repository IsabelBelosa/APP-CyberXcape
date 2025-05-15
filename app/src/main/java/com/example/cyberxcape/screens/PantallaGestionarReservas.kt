package com.example.cyberxcape.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cyberxcape.componentes.MainLayout
import com.example.cyberxcape.model.Reserva
import com.example.cyberxcape.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGestionarReservas(navController: NavHostController) {

    var selectedItem by remember { mutableStateOf("reservas") }

    val reservas = remember { getReservasEjemplo() }

    MainLayout(
        navController = navController,
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(reservas) { reserva ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Negro),
                    border = BorderStroke(1.dp, Rosa),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("ID: ${reserva.id}", color = Blanco)
                        Text("Sala: ${reserva.nombreSala}", color = Rosa)
                        Text("Fecha: ${reserva.fecha}", color = Blanco)
                        Text("Teléfono: ${reserva.telefono}", color = Blanco)

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                navController.navigate("verInfo/${reserva.id}")
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

fun getReservasEjemplo(): List<Reserva> {
    return listOf(
        Reserva(1, "Neutral Hack", "08/05/2025", "123456789"),
        Reserva(2, "Estación Omega", "10/05/2025", "987654321"),
        Reserva(3, "ExperimentoX-33", "12/05/2025", "456123789")
    )
}
