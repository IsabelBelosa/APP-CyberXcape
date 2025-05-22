package com.example.cyberxcape

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import com.example.cyberxcape.componentes.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest

class MainLayoutTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testDrawerOpensOnMenuClick() {
        composeTestRule.onNodeWithTag("menuButton").performClick()
        composeTestRule.onNodeWithTag("drawerItem_inicio").assertIsDisplayed()
    }

    @Test
    fun menuButtonSeMuestra() {
        composeTestRule.onNodeWithTag("menuButton").assertExists()
    }

    @Test
    fun testNavigationToSalas() {
        composeTestRule.onNodeWithTag("menuButton").performClick()
        composeTestRule.onNodeWithTag("drawerItem_salas").performClick()
        composeTestRule.onNodeWithText("Salas", ignoreCase = true).assertExists()
    }

    @Test
    fun testLogoDisplayed() {
        composeTestRule.onNodeWithTag("logoImg").assertIsDisplayed()
    }

    @Test
    fun testNavigationToReservas() {
        composeTestRule.onNodeWithTag("menuButton").performClick()
        composeTestRule.onNodeWithTag("drawerItem_reservas").performClick()
        composeTestRule.onNodeWithText("Reservas", ignoreCase = true).assertExists()
    }

    @Test
    fun testClickLogoNavigatesToInicio() {
        composeTestRule.onNodeWithTag("logoImg").performClick()
        composeTestRule.onNodeWithText("Inicio", ignoreCase = true).assertExists()
    }
}
