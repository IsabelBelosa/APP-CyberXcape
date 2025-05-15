package com.example.cyberxcape

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cyberxcape.ui.theme.*
import com.example.cyberxcape.model.ViewModel_class
import java.util.*
import androidx.compose.ui.text.input.KeyboardType
import java.text.SimpleDateFormat
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.example.cyberxcape.componentes.MainLayout


@SuppressLint("DefaultLocale", "SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formulario(viewModel: ViewModel_class = viewModel(), navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
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

    MainLayout(
        navController = navController,
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it }
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
            OutlinedTextField(
                uiState.nombre, viewModel::onNombreChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                )
            )
            OutlinedTextField(
                uiState.apellidos, viewModel::onApellidosChange,
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                )
            )
            OutlinedTextField(
                uiState.telefono, viewModel::onTelefonoChange,
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                )
            )
            OutlinedTextField(
                uiState.numeroSocio, viewModel::onNumeroSocioChange,
                label = { Text("Número de Socio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                )
            )
            OutlinedTextField(
                uiState.email, viewModel::onEmailChange,
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                )
            )
            OutlinedTextField(
                uiState.numeroJugadores, viewModel::onNumeroJugadoresChange,
                label = { Text("Número de Jugadores") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                )

            )

            // Sala dropdown
            ExposedDropdownMenuBox(
                expanded = salaExpanded,
                onExpandedChange = { salaExpanded = !salaExpanded }) {
                OutlinedTextField(
                    value = if (uiState.sala.isEmpty()) "Seleccionar sala" else uiState.sala,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sala") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(salaExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Rosa,
                        focusedLabelColor = Rosa,
                        cursorColor = Rosa
                    )
                )
                ExposedDropdownMenu(
                    expanded = salaExpanded,
                    onDismissRequest = { salaExpanded = false }) {
                    salaOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = { viewModel.onSalaChange(option); salaExpanded = false })
                    }
                }
            }

            // Dificultad dropdown
            ExposedDropdownMenuBox(
                expanded = difExpanded,
                onExpandedChange = { difExpanded = !difExpanded }) {
                OutlinedTextField(
                    value = if (uiState.dificultad.isEmpty()) "Seleccionar dificultad" else uiState.dificultad,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Dificultad") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(difExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Rosa,
                        focusedLabelColor = Rosa,
                        cursorColor = Rosa
                    )
                )
                ExposedDropdownMenu(
                    expanded = difExpanded,
                    onDismissRequest = { difExpanded = false }) {
                    difOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = { viewModel.onDificultadChange(option); difExpanded = false })
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
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = "Seleccionar fecha",
                        modifier = Modifier.clickable { datePickerDialog.show() })
                },
                modifier = Modifier.fillMaxWidth().clickable {
                    datePickerDialog.show()
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                )
            )

            // Hora
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    uiState.horaInicio, viewModel::onHoraInicioChange,
                    label = { Text("Hora Inicio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Rosa,
                        focusedLabelColor = Rosa,
                        cursorColor = Rosa
                    )
                )
                OutlinedTextField(
                    uiState.horaFin, viewModel::onHoraFinChange,
                    label = { Text("Hora Fin") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Rosa,
                        focusedLabelColor = Rosa,
                        cursorColor = Rosa
                    )
                )
            }

            // Comentarios
            OutlinedTextField(
                uiState.comentarios, viewModel::onComentariosChange,
                label = { Text("Comentarios") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                )
            )

            // Error
            uiState.errorMensaje?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            // Enviar
            Button(
                onClick = { viewModel.validarYEnviar() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Rosa)
            ) { Text("Enviar", color = Blanco) }

            // Email Intent
            if (uiState.envioExitoso) {
                LaunchedEffect(uiState.envioExitoso) {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "message/rfc822"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("diazcanoignacio@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "Formulario de datos")
                        putExtra(
                            Intent.EXTRA_TEXT,
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
                            """.trimIndent()
                        )
                    }
                    context.startActivity(Intent.createChooser(intent, "Enviar email con..."))
                }
            }
        }
    }
}
