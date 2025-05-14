package com.example.cyberxcape

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.sp
import com.example.cyberxcape.ui.theme.*
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.LatLng
import androidx.compose.foundation.layout.fillMaxSize
import com.google.android.gms.maps.model.CameraPosition
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLocalizacion(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutinescope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("localizacion") }

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
                            coroutinescope.launch { drawerState.close() }
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
                            Spacer(modifier = Modifier.width(8.dp))
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
                            coroutinescope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Blanco)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Negro,
                        titleContentColor = Blanco
                    )
                )
            },
            containerColor = Negro
        ) { innerPadding ->
            //Esto es para realizar la vista de las salas como una columna
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Negro)
            ) {
                // Título
                Text(
                    text = "Contacto y Localización",
                    color = Azul,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        .border(
                            BorderStroke(2.dp, Rosa),
                            shape = RoundedCornerShape(12.dp) // Bordes redondeados
                        )
                        .padding(8.dp),
                    textAlign =TextAlign.Center
                )

                // Dirección
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Ubicación", tint = Rosa)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Calle Sacramento, 19, 11001 Candelaria Cádiz", color = Blanco)
                }

                // Teléfonos
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                    Icon(Icons.Default.Call, contentDescription = "Teléfono", tint = Rosa)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("657 88 30 89 / 956 12 23 44", color = Blanco)
                }

                // Email
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                    Icon(Icons.Default.Email, contentDescription = "Email", tint = Rosa)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("CyberXcape@gmail.com", color = Blanco)
                }
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "¿Cómo llegar?",
                    color = Azul,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        .border(
                            BorderStroke(2.dp, Rosa),
                            shape = RoundedCornerShape(12.dp) // Bordes redondeados
                        )
                        .padding(8.dp),
                    textAlign =TextAlign.Center
                )
                Text("Puedes llegar mediante autobús o tren:", color = Blanco,textAlign =TextAlign.Center)
                // Enlaces
                Column(modifier = Modifier.padding(8.dp)) {
                    val context = LocalContext.current

                    Text(
                        text = "Enlace de Consorcio de Transportes",
                        color = Azul,
                        textAlign =TextAlign.Center,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_VIEW, "https://cmtbc.es/".toUri())
                            context.startActivity(intent)
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Enlace de Renfe",
                        color = Azul,
                        textAlign =TextAlign.Center,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_VIEW,
                                "https://www.renfe.com/es/es/cercanias/cercanias-cadiz".toUri())
                            context.startActivity(intent)
                        }
                    )
                }

                // Mapa
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(top = 16.dp)
                ) {
                    MapaUbicacion()
                }
            }
        }
    }
}



@Composable
fun MapaUbicacion() {
    val cadiz = LatLng(36.5298, -6.2926) // Coordenadas aproximadas de Candelaria, Cádiz
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cadiz, 16f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = cadiz),
            title = "CyberXcape",
            snippet = "Calle Sacramento, 19, Cádiz"
        )
    }
}