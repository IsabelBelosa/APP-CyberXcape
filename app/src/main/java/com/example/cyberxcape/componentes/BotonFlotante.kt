package com.example.cyberxcape.componentes


import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.cyberxcape.ui.theme.Negro
import com.example.cyberxcape.ui.theme.Rosa

@Composable
fun MyFloatingAcbu(
    navController: NavController, // <-- Se recibe como parámetro
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = {
            navController.navigate("formulario")
        },
        containerColor = Rosa,
        contentColor = Negro,
        shape = CircleShape,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "reservas"
        )
    }
}

