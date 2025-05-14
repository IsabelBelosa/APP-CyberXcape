package com.example.cyberxcape

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.*
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
import com.example.cyberxcape.ui.theme.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import com.example.cyberxcape.model.Sala
import java.io.IOException
import java.io.OutputStream


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
            floatingActionButton = { MyFloatingAcbu(navController = navController) },
            floatingActionButtonPosition = FabPosition.End,
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
                SalaView()
            }
        }
    }
}

@Composable

fun SalaView() {
    val salas = getSalas()
    val context = LocalContext.current

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        // Boton de descargar y abrir PDF
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { guardarYAbrirPdf(context, "salas_info.pdf") },
                    colors = ButtonDefaults.buttonColors(containerColor = Rosa)
                ) {
                    Text("Descargar PDF", color = Blanco)
                }
            }
        }

        // Titulo de la sala
        items(salas) { sala ->
            Text(
                text = sala.nombre,
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
            // Card de la sala
            SalaCard(sala = sala, context = context)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
// Card de la sala, que contiene la imagen, el nombre, la descripción y la tabla de servicios y obtenemos la información de la sala
fun SalaCard(sala: Sala, context: Context) {
    Card(
        border = BorderStroke(2.dp, Rosa),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Negro)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Image(
                painter = painterResource(id = sala.imagen),
                contentDescription = "Imagen ${sala.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                sala.bienvenidos,
                fontSize = 14.sp,
                color = Blanco,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
            )
            Text(
                sala.descripcion,
                fontSize = 14.sp,
                color = Blanco,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))
            SalaTable(data = sala.tabla)

            Spacer(modifier = Modifier.height(8.dp))



        }
    }
}

@Composable
fun SalaTable(data: List<List<String>>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val titles = data.first()
        Row(modifier = Modifier.fillMaxWidth()) {
            titles.forEach {
                Text(
                    text = it,
                    modifier = Modifier
                        .weight(1f)
                        .background(Rosa)
                        .padding(8.dp),
                    color = Blanco,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        data.drop(1).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach {
                    Text(
                        text = it,
                        modifier = Modifier
                            .weight(1f)
                            .background(Negro)
                            .padding(8.dp),
                        color = Blanco,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


fun getSalas(): List<Sala> {
    return listOf(
        Sala(
            "Neutral Hack",
            "¡Bienvenidos a Neutral Hack!",
            "Hackea la IA y escapa antes de que sea demasiado tarde.",
            R.drawable.sala_a,
            listOf(
                listOf("Servicio", "Disponible"),
                listOf("Wi-Fi", "Sí"),
                listOf("Luces LED", "Sí"),
                listOf("Pantallas táctiles", "Sí")
            )
        ),

        Sala(
            "Estación Omega",
            "¡Bienvenidos a Estación Omega!",
            "Sobrevive en la última base humana tras un fallo catastrófico.",
            R.drawable.sala_b,
            listOf(
                listOf("Servicio", "Disponible"),
                listOf("Wi-Fi", "Sí"),
                listOf("Sonido envolvente", "Sí"),
                listOf("Escape remoto", "Sí")
            )
        ),
        Sala(
            "ExperimentoX-33",
            "¡Bienvenidos a ExperimentoX-33!",
            "Eres el sujeto de prueba de un experimento clasificado.",
            R.drawable.sala_c,
            listOf(
                listOf("Servicio", "Disponible"),
                listOf("Sala insonorizada", "Sí"),
                listOf("Control biométrico", "Sí"),
                listOf("Luces LED", "Sí")
            )
        )
    )
}



fun guardarYAbrirPdf(context: Context, nombreArchivo: String) {
    // 1. Obtener el ContentResolver para insertar y leer URIs en MediaStore.
    val resolver = context.contentResolver

    // 2. Asegurarnos de que el nombre incluya la extensión ".pdf".
    val nombreArchivoConExtension =
        if (nombreArchivo.endsWith(".pdf")) nombreArchivo else "$nombreArchivo.pdf"

    // 3. Preparar los valores que describen el archivo en MediaStore.
    val contentValues = ContentValues().apply {
        put(MediaStore.Downloads.DISPLAY_NAME, nombreArchivoConExtension)
        put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
    }

    // 4. Insertar un nuevo registro en MediaStore→Descargas. Obtendremos la URI donde escribir.
    val uri: Uri? =
        resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

    if (uri != null) {
        try {
            // 5. Abrir un OutputStream hacia la URI y copiar los bytes del asset.
            resolver.openOutputStream(uri).use { outputStream ->
                context.assets.open(nombreArchivoConExtension).use { inputStream ->
                    // Copiamos el contenido del asset al almacenamiento público.
                    inputStream.copyTo(outputStream as OutputStream)
                }
            }

            // 6. Notificar al usuario de que el PDF ya está guardado.
            Toast.makeText(context, "PDF guardado en Descargas", Toast.LENGTH_SHORT).show()

            // 7. Crear un Intent para abrir el PDF recién guardado.
            val intent = Intent(Intent.ACTION_VIEW).apply {
                // Indicamos la URI y el tipo de contenido
                setDataAndType(uri, "application/pdf")
                // Concede permiso de lectura temporal a la app que abra el PDF
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                // Si se invoca desde un contexto no-Activity, crea una nueva tarea
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            // 8. Lanzar el Intent; el sistema elegirá la app adecuada.
            context.startActivity(intent)

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Error al guardar o abrir el PDF",
                Toast.LENGTH_SHORT
            ).show()
        }
    } else {
        Toast.makeText(context, "No se pudo crear el archivo", Toast.LENGTH_SHORT).show()
    }
}

