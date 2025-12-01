package com.example.veterinaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.veterinaria.ui.AgendaScreen
import com.example.veterinaria.ui.ConsultaFormScreen
import com.example.veterinaria.ui.DuenosScreen
import com.example.veterinaria.ui.HomeScreen
import com.example.veterinaria.ui.MascotaFormScreen
import com.example.veterinaria.ui.MascotasScreen
import com.example.veterinaria.ui.VeterinariosScreen
import com.example.veterinaria.viewmodel.MainViewModel
import com.example.veterinaria.ui.DuenoFormScreen
import com.example.veterinaria.ui.VeterinarioFormScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VeterinariaApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeterinariaApp() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val loadingMessage by viewModel.loadingMessage.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val items = listOf(
                    Screen("home", "Inicio", Icons.Default.Home),
                    Screen("mascotas", "Mascotas", Icons.Default.Pets),
                    Screen("duenos", "Dueños", Icons.Default.People),
                    Screen("veterinarios", "Veterinarios", Icons.Default.Person),
                    Screen("agenda", "Agenda", Icons.Default.DateRange)
                )
                items.forEach { screen ->
                    IconButton(
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(screen.icon, contentDescription = screen.label, tint = if (currentRoute == screen.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(viewModel, onNavigate = { route -> navController.navigate(route) }) }
                composable("mascotas") { MascotasScreen(viewModel, navController) }
                composable("duenos") { DuenosScreen(viewModel, navController) } 
                composable("veterinarios") { VeterinariosScreen(viewModel, navController) }
                composable("agenda") { AgendaScreen(viewModel, navController) }
                composable(
                    "mascotaForm/{mascotaId}",
                    arguments = listOf(navArgument("mascotaId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val mascotaId = backStackEntry.arguments?.getInt("mascotaId")
                    if (mascotaId != null && mascotaId != 0) {
                        MascotaFormScreen(viewModel, navController, mascotaId)
                    } else {
                        MascotaFormScreen(viewModel, navController, null)
                    }
                }
                 composable(
                    "duenoForm/{duenoId}",
                    arguments = listOf(navArgument("duenoId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val duenoId = backStackEntry.arguments?.getString("duenoId")
                    if (duenoId != null && duenoId != "0") {
                        DuenoFormScreen(viewModel, navController, duenoId)
                    } else {
                        DuenoFormScreen(viewModel, navController, null)
                    }
                }
                composable(
                    "veterinarioForm/{veterinarioId}",
                    arguments = listOf(navArgument("veterinarioId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val veterinarioId = backStackEntry.arguments?.getInt("veterinarioId")
                    if (veterinarioId != null && veterinarioId != 0) {
                        VeterinarioFormScreen(viewModel, navController, veterinarioId)
                    } else {
                        VeterinarioFormScreen(viewModel, navController, null)
                    }
                }
                composable(
                    "consultaForm/{consultaId}",
                    arguments = listOf(navArgument("consultaId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val consultaId = backStackEntry.arguments?.getInt("consultaId")
                    if (consultaId != null && consultaId != 0) {
                        ConsultaFormScreen(viewModel, navController, consultaId)
                    } else {
                        ConsultaFormScreen(viewModel, navController, null)
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shadowElevation = 4.dp,
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = loadingMessage)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class Screen(val route: String, val label: String, val icon: ImageVector)
