package com.example.cyberxcape.componentes

import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.cyberxcape.ui.theme.Blanco
import com.example.cyberxcape.ui.theme.Gris
import com.example.cyberxcape.ui.theme.Negro
import com.example.cyberxcape.ui.theme.Rosa
import com.example.cyberxcape.ui.theme.pressStart2P
import androidx.navigation.NavHostController
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.example.cyberxcape.R


data class NavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    navController: NavHostController,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp),
                drawerContainerColor = Negro
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                val items = listOf(
                    NavigationItem("Inicio", "inicio", Icons.Filled.Home, Icons.Outlined.Home),
                    NavigationItem("Salas", "salas", Icons.Filled.Lock, Icons.Outlined.Lock),
                    NavigationItem(
                        "Gestionar Reservas",
                        "reservas",
                        Icons.Filled.Info,
                        Icons.Outlined.Info
                    ),
                    NavigationItem(
                        "Localización",
                        "localizacion",
                        Icons.Filled.LocationOn,
                        Icons.Outlined.LocationOn
                    ),
                )

                items.forEach { item ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 16.sp,
                                fontWeight = if (item.route == selectedItem) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selected = item.route == selectedItem,
                        onClick = {
                            onItemSelected(item.route)
                            coroutineScope.launch { drawerState.close() }
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(
                                imageVector = if (item.route == selectedItem) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Rosa.copy(alpha = 0.2f),
                            unselectedContainerColor = Color.Transparent,
                            selectedIconColor = Rosa,
                            selectedTextColor = Rosa,
                            unselectedIconColor = Blanco,
                            unselectedTextColor = Blanco
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("drawerItem_${item.route}") // TAG por ruta
                    )

                    HorizontalDivider(thickness = 1.dp, color = Gris)
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("CYBER", color = Blanco, fontFamily = pressStart2P)
                            Text("X", color = Rosa, fontWeight = FontWeight.Bold, fontFamily = pressStart2P)
                            Text("CAPE", color = Blanco, fontFamily = pressStart2P)
                            IconButton(
                                onClick = { navController.navigate("inicio") }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "Logo",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .testTag("logoImg") // TAG para el logo
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { drawerState.open() }
                            },
                            modifier = Modifier.testTag("menuButton") // TAG para el botón de menú
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Blanco)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Negro,
                        titleContentColor = Blanco
                    )
                )
            },
            containerColor = Negro,
            content = content
        )
    }
}
