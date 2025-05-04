package com.example.cyberxcape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.cyberxcape.ui.theme.Blanco
import com.example.cyberxcape.ui.theme.Gris
import com.example.cyberxcape.ui.theme.Negro
import com.example.cyberxcape.ui.theme.Rosa
import com.example.cyberxcape.ui.theme.pressStart2P
import androidx.navigation.NavHostController
import androidx.compose.foundation.clickable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(navController: NavHostController) {
    // Estado del menú lateral (abierto o cerrado)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // Alcance de corrutina para manejar acciones asíncronas
    val scope = rememberCoroutineScope()

    // Contenedor que incluye el menu lateral (drawerContent) y el contenido principal (scaffold)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Contenido del menú lateral
            ModalDrawerSheet(
                drawerContainerColor = Negro, // Color de fondo del menú
                drawerContentColor = Blanco, // Color del texto del menú
                modifier = Modifier
                    .fillMaxHeight() // Altura completa
                    .width(250.dp) // Ancho del menú
            ) {
                // Opciones del menú organizadas en una columna
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Salas",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                                            .clickable {
                                                scope.launch { drawerState.close() }
                                                navController.navigate("salas")
                                            }
                    )
                    HorizontalDivider(thickness = 1.dp, color = Gris) // Separador
                    Text(
                        "Gestionar Reservas",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                                            .clickable {
                                                scope.launch { drawerState.close() }
                                                navController.navigate("reservas")
                                            }
                    )
                    HorizontalDivider(thickness = 1.dp, color = Gris) // Separador
                    Text(
                        "Localización",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 12.dp)
                                            .clickable {
                                                scope.launch { drawerState.close() }
                                                navController.navigate("localizacion")
                                            }
                    )
                }
            }
        }
    )
    {
        // Incluye la interfaz del usuario (TopAppBar) y el área de contenido principal
        Scaffold(
            topBar = {
                // Barra superior con título y botón de menú lateral
                TopAppBar(
                    title = {
                        // Título estilizado con el logo
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(8.dp)) // Espaciado inicial
                            Text("CYBER", color = Blanco, fontFamily = pressStart2P)
                            Text("X", color = Rosa, fontWeight = FontWeight.Bold, fontFamily = pressStart2P)
                            Text("CAPE", color = Blanco, fontFamily = pressStart2P)
                            // Botón que navega a la pantalla "inicio"
                            IconButton(onClick = { navController.navigate("inicio") }) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    },
                    // Botón para abrir el menú lateral
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() } // Abre el menú lateral
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Blanco)
                        }
                    },
                    // Colores de la barra superior
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Negro, // Fondo negro
                        titleContentColor = Blanco // Texto blanco
                    )
                )
            },
            containerColor = Negro // Fondo del contenido principal
        ) { paddingValues ->
            // Contenedor principal de la pantalla
            Box(
                modifier = Modifier
                    .fillMaxSize() // Ocupa toda la pantalla
                    .padding(paddingValues) // Respeta los márgenes del Scaffold
            ) {
                // Imagen de fondo
                Image(
                    painter = painterResource(id = R.drawable.neutralhack),
                    contentDescription = "Fondo de la app",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Escala la imagen para cubrir toda la pantalla
                )

                // Fondo negro semitransparente sobre toda la pantalla
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )

                // Contenido centrado con fondo local semitransparente
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Contenedor con fondo local opaco para los elementos
                    Box(
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.7f)) // Fondo solo detrás del contenido
                            .padding(horizontal = 24.dp, vertical = 32.dp) // Espaciado interno
                    ) {
                        // Columna con el título, logo y descripción
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(20.dp) // Separación entre elementos
                        ) {
                            // Título estilizado
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("CYBER", color = Blanco, fontFamily = pressStart2P)
                                Text("X", color = Rosa, fontWeight = FontWeight.Bold, fontFamily = pressStart2P)
                                Text("CAPE", color = Blanco, fontFamily = pressStart2P)
                            }
                            // Logo de la app
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = Modifier.size(80.dp)
                            )
                            // Descripción de la app
                            Text(
                                "No te quedes sin probar los mejores escaperooms de España!!",
                                color = Blanco,
                                fontFamily = pressStart2P,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 12.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                // Pie de página fijo al final de la pantalla
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(Color.Black.copy(alpha = 0.5f)) // Fondo negro semitransparente
                        .padding(8.dp)
                ) {
                    // Texto con los créditos
                    Text(
                        text = "© 2025 Isabel Becerra Losada & Paula Venegas Roldán — CyberXCape",
                        color = Blanco,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}
