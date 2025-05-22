package com.example.cyberxcape.screens


import androidx.compose.runtime.*
import androidx.compose.material3.*
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
import com.example.cyberxcape.ui.theme.Blanco
import com.example.cyberxcape.ui.theme.Rosa
import com.example.cyberxcape.ui.theme.pressStart2P
import androidx.navigation.NavHostController
import androidx.compose.ui.text.style.TextAlign
import com.example.cyberxcape.componentes.MainLayout
import com.example.cyberxcape.R




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(navController: NavHostController) {

    // Estado de ítem seleccionado
    var selectedItem by remember { mutableStateOf("inicio") }

    MainLayout(
        navController = navController,
        selectedItem = selectedItem,
        onItemSelected = { selectedItem = it }
    )

    { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sala_a),
                contentDescription = "Fondo de la app",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("CYBER", color = Blanco, fontFamily = pressStart2P)
                            Text(
                                "X",
                                color = Rosa,
                                fontWeight = FontWeight.Bold,
                                fontFamily = pressStart2P
                            )
                            Text("CAPE", color = Blanco, fontFamily = pressStart2P)
                        }
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(80.dp)
                        )
                        Text(
                            "No te quedes sin probar los mejores escaperooms de España!!",
                            color = Blanco,
                            fontFamily = pressStart2P,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Pie de página fijo al final de la pantalla
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp),
            ) {
                Text(
                    text = "© 2025 Isabel Becerra Losada & Paula Venegas Roldán — CyberXCape",
                    color = Blanco,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

