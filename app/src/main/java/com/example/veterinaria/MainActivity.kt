package com.example.veterinaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.veterinaria.ui.AgendaSelectionScreen
import com.example.veterinaria.ui.CalendarioScreen
import com.example.veterinaria.ui.ConsultaFormScreen
import com.example.veterinaria.ui.DuenoFormScreen
import com.example.veterinaria.ui.DuenosScreen
import com.example.veterinaria.ui.HomeScreen
import com.example.veterinaria.ui.MascotaFormScreen
import com.example.veterinaria.ui.MascotasScreen
import com.example.veterinaria.ui.TopBar
import com.example.veterinaria.ui.VeterinarioFormScreen
import com.example.veterinaria.ui.VeterinariosScreen
import com.example.veterinaria.viewmodel.MainViewModel

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

    Scaffold(
        topBar = {
            TopBar(onNavigateTo = { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = "home",
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() }
            ) {
                composable("home") { HomeScreen(viewModel, onNavigate = { route -> navController.navigate(route) }) }
                composable("mascotas") { MascotasScreen(viewModel, navController) }
                composable("duenos") { DuenosScreen(viewModel, navController) }
                composable("veterinarios") { VeterinariosScreen(viewModel, navController) }
                composable("agenda") { AgendaSelectionScreen(viewModel, navController) }
                composable("calendario") { CalendarioScreen(viewModel) }

                composable(
                    "mascotaForm/{mascotaId}?duenoId={duenoId}",
                    arguments = listOf(
                        navArgument("mascotaId") { type = NavType.IntType },
                        navArgument("duenoId") { type = NavType.StringType; nullable = true }
                    )
                ) { backStackEntry ->
                    val mascotaId = backStackEntry.arguments?.getInt("mascotaId")
                    val duenoId = backStackEntry.arguments?.getString("duenoId")
                    MascotaFormScreen(viewModel, navController, if (mascotaId == 0) null else mascotaId, duenoId)
                }
                composable(
                    "duenoForm/{duenoId}",
                    arguments = listOf(navArgument("duenoId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val duenoId = backStackEntry.arguments?.getString("duenoId")
                    DuenoFormScreen(viewModel, navController, if (duenoId == "0") null else duenoId)
                }
                composable(
                    "veterinarioForm/{veterinarioId}",
                    arguments = listOf(navArgument("veterinarioId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val veterinarioId = backStackEntry.arguments?.getInt("veterinarioId")
                    VeterinarioFormScreen(viewModel, navController, if (veterinarioId == 0) null else veterinarioId)
                }
                composable(
                    "consultaForm/{consultaId}?duenoId={duenoId}",
                    arguments = listOf(
                        navArgument("consultaId") { type = NavType.IntType },
                        navArgument("duenoId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val consultaId = backStackEntry.arguments?.getInt("consultaId")
                    val duenoId = backStackEntry.arguments?.getString("duenoId")!!
                    ConsultaFormScreen(viewModel, navController, if (consultaId == 0) null else consultaId, duenoId)
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
