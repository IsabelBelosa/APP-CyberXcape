package com.example.cyberxcape

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cyberxcape.ui.theme.Blanco
import com.example.cyberxcape.ui.theme.Negro
import com.example.cyberxcape.ui.theme.Rojo
import com.example.cyberxcape.ui.theme.Rosa
import com.example.cyberxcape.model.ViewModel_class
import java.util.Calendar
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cyberxcape.ui.theme.Gris
import com.example.cyberxcape.ui.theme.pressStart2P
import kotlinx.coroutines.launch


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(viewModel: ViewModel_class = viewModel(), navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("salas") }

    val showTimePickerStart = remember { mutableStateOf(false) }
    val showTimePickerEnd = remember { mutableStateOf(false) }
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    var expandedSala by remember { mutableStateOf(false) }
    var expandedDificultad by remember { mutableStateOf(false) }

    val salas = listOf("Sala A", "Sala B", "Sala C")
    val dificultades = listOf("Fácil", "Media", "Difícil")



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { ModalDrawerSheet(
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
        } }
    ) {
        Scaffold(
            topBar = { TopAppBar(
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
            ) },
            floatingActionButton = { MyFloatingAcbu() },
            floatingActionButtonPosition = FabPosition.End,
            containerColor = Negro
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextField(
                    value = uiState.nombre,
                    onValueChange = viewModel::onNombreChange,
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = uiState.apellidos,
                    onValueChange = viewModel::onApellidosChange,
                    label = { Text("Apellidos") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = uiState.telefono,
                    onValueChange = viewModel::onTelefonoChange,
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = uiState.socio,
                    onValueChange = viewModel::onSocioChange,
                    label = { Text("Número Carnet Socio") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = uiState.jugadores,
                    onValueChange = viewModel::onJugadoresChange,
                    label = { Text("Número de jugadores") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(expanded = expandedSala, onExpandedChange = { expandedSala = !expandedSala }) {
                    TextField(
                        value = uiState.sala,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Seleccione Sala") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSala) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expandedSala, onDismissRequest = { expandedSala = false }) {
                        salas.forEach { sala ->
                            DropdownMenuItem(
                                text = { Text(sala) },
                                onClick = {
                                    viewModel.onSalaChange(sala)
                                    expandedSala = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(expanded = expandedDificultad, onExpandedChange = { expandedDificultad = !expandedDificultad }) {
                    TextField(
                        value = uiState.dificultad,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Seleccione Dificultad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDificultad) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expandedDificultad, onDismissRequest = { expandedDificultad = false }) {
                        dificultades.forEach { dif ->
                            DropdownMenuItem(
                                text = { Text(dif) },
                                onClick = {
                                    viewModel.onDificultadChange(dif)
                                    expandedDificultad = false
                                }
                            )
                        }
                    }
                }

                TextField(
                    value = uiState.fecha,
                    onValueChange = {},
                    label = { Text("Seleccione Fecha") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker.value = true }
                )

                TextField(
                    value = uiState.hora,
                    onValueChange = {},
                    label = { Text("Seleccione Hora") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTimePicker.value = true }
                )

                TextField(
                    value = uiState.comentario,
                    onValueChange = viewModel::onComentarioChange,
                    label = { Text("Comentario") },
                    modifier = Modifier.fillMaxWidth()
                )

                uiState.errorMensaje?.let {
                    Text(text = it, color = Rojo)
                }

                Button(
                    onClick = {
                        viewModel.validarYEnviar()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Rosa)
                ) {
                    Text("Enviar")
                }

                if (uiState.envioExitoso) {
                    LaunchedEffect(Unit) {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("diazcanoignacio@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "Formulario de datos")
                            putExtra(Intent.EXTRA_TEXT, """
                                Nombre: ${uiState.nombre}
                                Apellidos: ${uiState.apellidos}
                                Teléfono: ${uiState.telefono}
                                Email: ${uiState.email}
                                Nº Carnet de Socio: ${uiState.socio}
                                Nº Jugadores: ${uiState.jugadores}
                                Sala: ${uiState.sala}
                                Dificultad: ${uiState.dificultad}
                                Fecha: ${uiState.fecha}
                                Hora: ${uiState.hora}
                                Comentario: ${uiState.comentario}
               
                            """.trimIndent())
                        }
                        context.startActivity(Intent.createChooser(intent, "Enviar email con..."))
                        viewModel.onEmailChange("") // Reseteo ejemplo
                    }
                }
                if (showDatePicker.value) {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                            viewModel.onFechaChange(fechaSeleccionada)
                            showDatePicker.value = false
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }

                if (showTimePickerStart.value) {
                    val calendar = Calendar.getInstance()
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            val horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute)
                            viewModel.onHoraInicioChange(horaSeleccionada)
                            showTimePickerStart.value = false
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                }

                if (showTimePickerEnd.value) {
                    val calendar = Calendar.getInstance()
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            val horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute)
                            viewModel.onHoraFinChange(horaSeleccionada)
                            showTimePickerEnd.value = false
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                }
            }
        }
    }
}