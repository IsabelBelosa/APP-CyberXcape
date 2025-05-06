package com.example.cyberxcape


import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cyberxcape.ui.theme.Negro
import com.example.cyberxcape.ui.theme.Rosa

@Composable
fun MyFloatingAcbu(modifier: Modifier = Modifier){
    FloatingActionButton(onClick = {},
        containerColor = Rosa,
        contentColor = Negro,
        shape = CircleShape,
        modifier = modifier) // Aquí se pasa el modificador
    {

        Icon(imageVector = Icons.Filled.Email, contentDescription = "email")
    }
}

