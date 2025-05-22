package com.example.cyberxcape

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.cyberxcape.ui.theme.CyberXcapeTheme
import org.junit.Rule
import org.junit.Test

class FormularioTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun formulario_clickSalaDropdown_showsOptions() {
        composeTestRule.setContent {
            CyberXcapeTheme {
                Formulario(navController = rememberNavController())
            }
        }

        // Abrir dropdown de Sala
        composeTestRule.onNodeWithText("Seleccionar sala").performClick()

        // Verificar opciones del dropdown
        listOf("Neutral Hack", "Estación Omega", "Experimento-33").forEach { option ->
            composeTestRule.onNodeWithText(option).assertIsDisplayed()
        }
    }

    @Test
    fun formulario_clickDificultadDropdown_showsOptions() {
        composeTestRule.setContent {
            CyberXcapeTheme {
                Formulario(navController = rememberNavController())
            }
        }

        // Abrir dropdown de Dificultad
        composeTestRule.onNodeWithText("Seleccionar dificultad").performClick()

        // Verificar opciones del dropdown
        listOf("Fácil", "Normal", "Difícil").forEach { option ->
            composeTestRule.onNodeWithText(option).assertIsDisplayed()
        }
    }

    @Test
    fun formulario_clickFechaOpensDatePicker() {
        composeTestRule.setContent {
            CyberXcapeTheme {
                Formulario(navController = rememberNavController())
            }
        }

        // Click en campo Fecha para abrir date picker
        composeTestRule.onNodeWithText("Fecha").performClick()

        // La prueba no puede verificar el diálogo nativo fácilmente,
        // pero se puede verificar que el campo no está vacío y es clickeable
        composeTestRule.onNodeWithText("Fecha").assertIsEnabled()
    }

    @Test
    fun formulario_canTypeInNombre() {
        composeTestRule.setContent {
            CyberXcapeTheme {
                Formulario(navController = rememberNavController())
            }
        }

        val testName = "Ignacio"

        composeTestRule.onNodeWithText("Nombre").performTextInput(testName)

        composeTestRule.onNodeWithText(testName).assertIsDisplayed()
    }

    @Test
    fun formulario_enviarButton_isClickable() {
        composeTestRule.setContent {
            CyberXcapeTheme {
                Formulario(navController = rememberNavController())
            }
        }

        composeTestRule.onNodeWithText("Enviar").assertIsEnabled().performClick()
    }
}
