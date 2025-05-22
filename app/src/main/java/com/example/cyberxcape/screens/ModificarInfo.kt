package com.example.cyberxcape.screens


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cyberxcape.componentes.MainLayout
import com.example.cyberxcape.model.ViewModel_reservas
import com.example.cyberxcape.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.cyberxcape.model.ReservaFormulario
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Date
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll




@Composable
fun ModificarInfo(
    reservaId: String,
    navController: NavHostController,
    viewModel: ViewModel_reservas = viewModel()
) {
    val reservaFormulario by viewModel.reservaFormulario.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()
    var selectedItem by remember { mutableStateOf("reservas") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(reservaId) {
        viewModel.cargarReservaPorId(reservaId.trim())
    }

    if (cargando) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val reserva = reservaFormulario
    if (reserva == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Reserva no encontrada", color = MaterialTheme.colorScheme.error)
        }
        return
    }

    // Parsear fecha existente a Date
    // Para mostrar la fecha en texto
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.ENGLISH) }
    var fechaDate by remember(reserva) { mutableStateOf(reserva.fecha ?: Date()) }

    // Estados locales para cada campo
    var nombre by remember(reserva) { mutableStateOf(reserva.nombre) }
    var apellidos by remember(reserva) { mutableStateOf(reserva.apellidos) }
    var telefono by remember(reserva) { mutableStateOf(reserva.telefono) }
    var numeroSocio by remember(reserva) { mutableStateOf(reserva.numeroSocio) }
    var email by remember(reserva) { mutableStateOf(reserva.email) }
    var numeroJugadores by remember(reserva) { mutableStateOf(reserva.numeroJugadores.toString()) }
    var sala by remember(reserva) { mutableStateOf(reserva.sala) }
    var dificultad by remember(reserva) { mutableStateOf(reserva.dificultad) }

    var horaInicio by remember(reserva) { mutableStateOf(reserva.horaInicio) }
    var horaFin by remember(reserva) { mutableStateOf(reserva.horaFin) }
    var comentarios by remember(reserva) { mutableStateOf(reserva.comentarios) }

    // DatePickerDialog para seleccionar fecha
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { time = fechaDate }
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                fechaDate = calendar.time
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    MainLayout(
        navController = navController,
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Editar Reserva", style = MaterialTheme.typography.titleLarge, color = Rosa)

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = numeroSocio,
                onValueChange = { numeroSocio = it },
                label = { Text("Número de Socio") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = numeroJugadores,
                onValueChange = { numeroJugadores = it },
                label = { Text("Número de Jugadores") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = sala,
                onValueChange = { sala = it },
                label = { Text("Sala") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = dificultad,
                onValueChange = { dificultad = it },
                label = { Text("Dificultad") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = dateFormat.format(fechaDate),
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Seleccionar fecha",
                        modifier = Modifier.clickable { datePickerDialog.show() }
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = horaInicio,
                onValueChange = { horaInicio = it },
                label = { Text("Hora de Inicio") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = horaFin,
                onValueChange = { horaFin = it },
                label = { Text("Hora de Fin") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )

            OutlinedTextField(
                value = comentarios,
                onValueChange = { comentarios = it },
                label = { Text("Comentarios") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Rosa,
                    focusedLabelColor = Rosa,
                    cursorColor = Rosa
                ),
                textStyle = LocalTextStyle.current.copy(color = Blanco)
            )


            if (error != null) {
                Text(text = error!!, color = MaterialTheme.colorScheme.error)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            val reservaActualizada = ReservaFormulario(
                                id = reservaId,
                                nombre = nombre,
                                apellidos = apellidos,
                                telefono = telefono,
                                numeroSocio = numeroSocio,
                                email = email,
                                numeroJugadores = numeroJugadores.toIntOrNull() ?: 1,
                                sala = sala,
                                dificultad = dificultad,
                                fecha = fechaDate,  // Aquí pasa el Date directamente
                                horaInicio = horaInicio,
                                horaFin = horaFin,
                                comentarios = comentarios
                            )
                            viewModel.actualizarReserva(reservaActualizada)
                            Toast.makeText(context, "Reserva actualizada correctamente", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Rosa)
                ) {
                    Text("Actualizar", color = Blanco)
                }


                Button(
                    onClick = {
                        scope.launch {
                            viewModel.eliminarReserva(reservaId.trim())
                            Toast.makeText(context, "Reserva eliminada correctamente", Toast.LENGTH_SHORT).show()
                            navController.navigate("reservas") {
                                popUpTo("reservas") { inclusive = true }
                                launchSingleTop = true
                            }

                        }

                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Rojo)
                ) {
                    Text("Eliminar", color = Blanco)
                }
            }
        }
    }
}

