package com.example.cyberxcape.componentes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cyberxcape.Formulario
import com.example.cyberxcape.model.ViewModel_class
import com.example.cyberxcape.screens.PantallaGestionarReservas
import com.example.cyberxcape.screens.PantallaInicio
import com.example.cyberxcape.screens.PantallaLocalizacion
import com.example.cyberxcape.screens.PantallaSalas


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { PantallaInicio(navController) }
        composable("salas") { PantallaSalas(navController) }
        composable ("reservas"){ PantallaGestionarReservas(navController) }
        composable("formulario") {
            val viewModel: ViewModel_class = viewModel() // Obtener el ViewModel
            Formulario(viewModel = viewModel, navController) // Pasar el ViewModel a Formulario
        }
        composable("localizacion") { PantallaLocalizacion(navController) }
    }
}

