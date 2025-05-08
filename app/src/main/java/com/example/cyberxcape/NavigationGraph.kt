package com.example.cyberxcape

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { PantallaInicio(navController) }
        composable("salas") { PantallaSalas(navController) }
        composable("reservas") { PantallaReservas() }
        composable("localizacion") { PantallaLocalizacion() }
    }
}