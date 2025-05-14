package com.example.cyberxcape

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import com.example.cyberxcape.ui.theme.*
import com.example.cyberxcape.model.ViewModel_class
import java.util.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@SuppressLint("DefaultLocale", "SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(viewModel: ViewModel_class = viewModel(), navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("reservas") }


    // Dropdown states
    var salaExpanded by remember { mutableStateOf(false) }
    var difExpanded by remember { mutableStateOf(false) }
    val salaOptions = listOf("Neutral Hack", "Estación Omega", "Experimento-33")
    val difOptions = listOf("Fácil", "Normal", "Difícil")

    // Date picker state
    val selectedDate = remember { mutableStateOf(Date()) }
    val datePickerDialog = remember {
        DatePickerDialog(context).apply {
            setOnDateSetListener { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance().apply { set(year, month, dayOfMonth) }
                selectedDate.value = calendar.time
                viewModel.onFechaChange(calendar.time)
            }
        }
    }

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
                            Spacer(Modifier.width(8.dp))
                            Text("CYBER", color = Blanco, fontFamily = pressStart2P)
                            Text("X", color = Rosa, fontWeight = FontWeight.Bold, fontFamily = pressStart2P)
                            Text("CAPE", color = Blanco, fontFamily = pressStart2P)
                            IconButton(onClick = { navController.navigate("inicio") }) {
                                Image(painter = painterResource(R.drawable.logo), contentDescription = "Logo", modifier = Modifier.size(40.dp))
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Blanco)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Negro)
                )
            },

            containerColor = Negro
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Personal info
                OutlinedTextField(uiState.nombre, viewModel::onNombreChange,
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(uiState.apellidos, viewModel::onApellidosChange,
                    label = { Text("Apellidos") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(uiState.telefono, viewModel::onTelefonoChange,
                    label = { Text("Teléfono") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(uiState.numeroSocio, viewModel::onNumeroSocioChange,
                    label = { Text("Número de Socio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(uiState.email, viewModel::onEmailChange,
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(uiState.numeroJugadores, viewModel::onNumeroJugadoresChange,
                    label = { Text("Número de Jugadores") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Sala dropdown
                ExposedDropdownMenuBox(expanded = salaExpanded, onExpandedChange = { salaExpanded = !salaExpanded }) {
                    OutlinedTextField(
                        value = if (uiState.sala.isEmpty()) "Seleccionar sala" else uiState.sala,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Sala") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(salaExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(expanded = salaExpanded, onDismissRequest = { salaExpanded = false }) {
                        salaOptions.forEach { option ->
                            DropdownMenuItem(text = { Text(option) }, onClick = { viewModel.onSalaChange(option); salaExpanded = false })
                        }
                    }
                }

                // Dificultad dropdown
                ExposedDropdownMenuBox(expanded = difExpanded, onExpandedChange = { difExpanded = !difExpanded }) {
                    OutlinedTextField(
                        value = if (uiState.dificultad.isEmpty()) "Seleccionar dificultad" else uiState.dificultad,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Dificultad") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(difExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(expanded = difExpanded, onDismissRequest = { difExpanded = false }) {
                        difOptions.forEach { option ->
                            DropdownMenuItem(text = { Text(option) }, onClick = { viewModel.onDificultadChange(option); difExpanded = false })
                        }
                    }
                }

                // Fecha input with calendar
                OutlinedTextField(
                    value = SimpleDateFormat("dd/MM/yyyy").format(selectedDate.value),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fecha") },
                    trailingIcon = {
                        Icon(Icons.Filled.DateRange, contentDescription = "Seleccionar fecha", modifier = Modifier.clickable { datePickerDialog.show() })
                    },
                    modifier = Modifier.fillMaxWidth().clickable { datePickerDialog.show() }

                )

                // Hora
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(uiState.horaInicio, viewModel::onHoraInicioChange,
                        label = { Text("Hora Inicio") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(uiState.horaFin, viewModel::onHoraFinChange,
                        label = { Text("Hora Fin") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                // Comentarios
                OutlinedTextField(uiState.comentarios, viewModel::onComentariosChange,
                    label = { Text("Comentarios") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Error
                uiState.errorMensaje?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                // Enviar
                Button(
                    onClick = { viewModel.validarYEnviar() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Rosa)
                ) { Text("Enviar", color= Blanco ) }

                // Email Intent
                if (uiState.envioExitoso) {
                    LaunchedEffect(uiState.envioExitoso) {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("diazcanoignacio@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "Formulario de datos")
                            putExtra(Intent.EXTRA_TEXT,
                                """
                                Nombre: ${uiState.nombre}
                                Apellidos: ${uiState.apellidos}
                                Teléfono: ${uiState.telefono}
                                Número de Socio: ${uiState.numeroSocio}
                                Email: ${uiState.email}
                                Número de Jugadores: ${uiState.numeroJugadores}
                                Sala: ${uiState.sala}
                                Dificultad: ${uiState.dificultad}
                                Fecha: ${uiState.fecha}
                                Hora Inicio: ${uiState.horaInicio}
                                Hora Fin: ${uiState.horaFin}
                                Comentarios: ${uiState.comentarios}
                                """.trimIndent())
                        }
                        context.startActivity(Intent.createChooser(intent, "Enviar email con..."))
                    }
                }
            }
        }
    }
}