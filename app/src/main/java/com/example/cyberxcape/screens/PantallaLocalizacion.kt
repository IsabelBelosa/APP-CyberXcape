package com.example.cyberxcape.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.sp
import com.example.cyberxcape.ui.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
import com.example.cyberxcape.componentes.MainLayout


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLocalizacion(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf("localizacion") }

    MainLayout(
        navController = navController,
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp) // Espaciado interno para el contenido
        ) {
            // Título principal
            Text(
                text = "Contacto y Localización",
                color = Azul,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        BorderStroke(2.dp, Rosa),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )

            // Dirección
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "Ubicación", tint = Rosa)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Calle Sacramento, 19, 11001 Candelaria Cádiz", color = Blanco)
            }

            // Teléfonos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(Icons.Default.Call, contentDescription = "Teléfono", tint = Rosa)
                Spacer(modifier = Modifier.width(8.dp))
                Text("657 88 30 89 / 956 12 23 44", color = Blanco)
            }

            // Email
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(Icons.Default.Email, contentDescription = "Email", tint = Rosa)
                Spacer(modifier = Modifier.width(8.dp))
                Text("CyberXcape@gmail.com", color = Blanco)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Subtítulo de cómo llegar
            Text(
                text = "¿Cómo llegar?",
                color = Azul,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        BorderStroke(2.dp, Rosa),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                "Puedes llegar mediante autobús o tren:",
                color = Blanco,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            val context = LocalContext.current

            // Enlaces
            Column {
                Text(
                    text = "Enlace de Consorcio de Transportes",
                    color = Azul,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, "https://cmtbc.es/".toUri())
                            context.startActivity(intent)
                        }
                        .padding(vertical = 4.dp)
                )
                Text(
                    text = "Enlace de Renfe",
                    color = Azul,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                "https://www.renfe.com/es/es/cercanias/cercanias-cadiz".toUri()
                            )
                            context.startActivity(intent)
                        }
                        .padding(vertical = 4.dp)
                )
            }

            // Mapa
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                MapaUbicacion()
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