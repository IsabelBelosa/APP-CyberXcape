package com.example.cyberxcape

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cyberxcape.ui.theme.Blanco
import com.example.cyberxcape.ui.theme.Gris
import com.example.cyberxcape.ui.theme.Negro
import com.example.cyberxcape.ui.theme.Rosa
import kotlinx.coroutines.launch
import android.content.Context
import android.os.Environment
import com.example.cyberxcape.ui.theme.pressStart2P
import java.io.File
import java.io.FileOutputStream




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSalas(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutinescope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("salas") }

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
                    NavigationItem(
                        title = "Inicio",
                        route = "inicio",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home
                    ),
                    NavigationItem(
                        title = "Salas",
                        route = "salas",
                        selectedIcon = Icons.Filled.Lock,
                        unselectedIcon = Icons.Outlined.Lock
                    ),
                    NavigationItem(
                        title = "Gestionar Reservas",
                        route = "reservas",
                        selectedIcon = Icons.Filled.Info,
                        unselectedIcon = Icons.Outlined.Info
                    ),
                    NavigationItem(
                        title = "Localización",
                        route = "localizacion",
                        selectedIcon = Icons.Filled.LocationOn,
                        unselectedIcon = Icons.Outlined.LocationOn
                    )
                )

                items.forEach { item ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 16.sp,
                                fontWeight = if (item.route == selectedItem) FontWeight.Bold else FontWeight.Normal,
                                color = Blanco // Color del texto blanco
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
                            coroutinescope.launch { drawerState.open() }
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
            floatingActionButton = { MyFloatingAcbu() },
            floatingActionButtonPosition = FabPosition.End,
            containerColor = Negro
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Negro) // Asegura que todo el fondo sea negro
            ) {
                SalaView()
            }
        }
    }
}

@Composable
fun SalaView() {
    val salas = getSalas()

    val context = LocalContext.current

    Button(
        onClick = {
            descargarPDF(context)
        },
        colors = ButtonDefaults.buttonColors(containerColor = Rosa),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text("Descargar PDF de Salas", color = Blanco)
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Negro) // Fondo de toda la lista
    ) {
        items(salas) { sala ->
            SalaCard(sala = sala) { sala ->
                Toast.makeText(context, "Sala ${sala.nombre}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun SalaCard(sala: Sala, onItemSelected: (Sala) -> Unit) {
    Card(
        border = BorderStroke(2.dp, Rosa), // Borde rosa de 2dp
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemSelected(sala) },
        colors = CardDefaults.cardColors(containerColor = Negro) // Fondo negro de la tarjeta
    ) {
        Column {
            Image(
                painter = painterResource(id = sala.imagen),
                contentDescription = "Imagen de ${sala.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = sala.nombre,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Blanco
            )

            Text(
                text = sala.descripcion,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 14.sp,
                color = Blanco
            )

            Spacer(modifier = Modifier.height(8.dp))

            SalaTable(data = sala.tabla)
        }
    }
}


@Composable
fun SalaTable(data: List<List<String>>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Títulos de la tabla con fondo rosa
        val titles = data.first()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            titles.forEach { title ->
                Text(
                    text = title,
                    modifier = Modifier
                        .weight(1f)
                        .background(Rosa) // Fondo rosa para los títulos
                        .padding(8.dp),
                    fontSize = 14.sp,
                    color = Blanco // Texto blanco
                )
            }
        }

        // Filas de la tabla con fondo negro
        data.drop(1).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                row.forEach { cell ->
                    Text(
                        text = cell,
                        modifier = Modifier
                            .weight(1f)
                            .background(Negro) // Fondo negro para las celdas de la tabla
                            .padding(8.dp),
                        fontSize = 12.sp,
                        color = Blanco // Texto blanco
                    )
                }
            }
        }
    }
}

data class Sala(
    val nombre: String,
    val descripcion: String,
    val imagen: Int,
    val tabla: List<List<String>> // Tabla de 3 columnas y 2 filas
)

fun getSalas(): List<Sala> {
    return listOf(
        Sala(
            nombre = "Neutral Hack",
            descripcion = "Una sala perfecta para reuniones.",
            imagen = R.drawable.sala_a,
            tabla = listOf(
                listOf("Jugadores", "Duración", "Edad"),
                listOf("Fila 1", "Fila 1", "Fila 1"),
            )
        ),
        Sala(
            nombre = "Estación Omega",
            descripcion = "Una sala ideal para eventos pequeños.",
            imagen = R.drawable.sala_b,
            tabla = listOf(
                listOf("Jugadores", "Duración", "Edad"),
                listOf("Fila 1", "Fila 1", "Fila 1"),
            )
        ),
        Sala(
            nombre = "ExperimentoX-33",
            descripcion = "Una sala con excelente ambiente para seminarios.",
            imagen = R.drawable.sala_c,
            tabla = listOf(
                listOf("Columna 1", "Columna 2", "Columna 3"),
                listOf("Fila 1", "Fila 1", "Fila 1"),
            )
        )
    )
}


fun descargarPDF(context: Context) {
    try {
        val assetManager = context.assets
        val inputStream = assetManager.open("salas_info.pdf")

        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val outFile = File(downloadsDir, "salas_info.pdf")

        val outputStream = FileOutputStream(outFile)

        inputStream.copyTo(outputStream)

        inputStream.close()
        outputStream.close()

        Toast.makeText(context, "PDF descargado en Descargas", Toast.LENGTH_LONG).show()

    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error al descargar el PDF", Toast.LENGTH_LONG).show()
    }
}
