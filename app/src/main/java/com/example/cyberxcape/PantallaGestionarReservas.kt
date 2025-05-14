package com.example.cyberxcape

import android.widget.Toast
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cyberxcape.model.Reserva
import com.example.cyberxcape.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGestionarReservas(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var selectedItem by remember { mutableStateOf("reservas") }

    val reservas = remember { getReservasEjemplo() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                Modifier
                    .fillMaxHeight()
                    .width(250.dp),
                drawerContainerColor = Negro
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                val items = listOf(
                    NavigationItem("Inicio", "inicio", Icons.Filled.Home, Icons.Outlined.Home),
                    NavigationItem("Salas", "salas", Icons.Filled.Lock, Icons.Outlined.Lock),
                    NavigationItem("Gestionar Reservas", "reservas", Icons.Filled.Info, Icons.Outlined.Info),
                    NavigationItem("Localización", "localizacion", Icons.Filled.LocationOn, Icons.Outlined.LocationOn)
                )

                items.forEach { item ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 16.sp,
                                fontWeight = if (item.route == selectedItem) FontWeight.Bold else FontWeight.Normal,
                                color = Blanco
                            )
                        },
                        selected = item.route == selectedItem,
                        onClick = {
                            selectedItem = item.route
                            coroutineScope.launch { drawerState.close() }
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(
                                imageVector = if (item.route == selectedItem) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title,
                            )
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Rosa.copy(alpha = 0.2f),
                            unselectedContainerColor = Color.Transparent,
                            selectedIconColor = Rosa,
                            selectedTextColor = Rosa,
                            unselectedIconColor = Blanco,
                            unselectedTextColor = Blanco
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    HorizontalDivider(thickness = 1.dp, color = Gris)
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(8.dp)) // Espaciado inicial
                            Text("CYBER", color = Blanco, fontFamily = pressStart2P)
                            Text("X", color = Rosa, fontWeight = FontWeight.Bold, fontFamily = pressStart2P)
                            Text("CAPE", color = Blanco, fontFamily = pressStart2P)
                            IconButton(onClick = { navController.navigate("inicio") }) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "inicio", tint = Blanco)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Negro,
                        titleContentColor = Blanco
                    )
                )
            },
            containerColor = Negro
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
}

fun getReservasEjemplo(): List<Reserva> {
    return listOf(
        Reserva(1, "Neutral Hack", "08/05/2025", "123456789"),
        Reserva(2, "Estación Omega", "10/05/2025", "987654321"),
        Reserva(3, "ExperimentoX-33", "12/05/2025", "456123789")
    )
}
