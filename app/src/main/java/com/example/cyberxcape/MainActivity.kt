// MainActivity.kt
package com.example.cyberxcape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.cyberxcape.ui.theme.CyberXcapeTheme
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyberXcapeTheme {
                val navController = rememberNavController()
                NavigationGraph(navController)
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun AppPreview() {
    CyberXcapeTheme {
        val navController = rememberNavController()
        NavigationGraph(navController = navController)
    }
}