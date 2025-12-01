package com.example.veterinaria.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.veterinaria.data.model.Veterinario
import com.example.veterinaria.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeterinarioFormScreen(viewModel: MainViewModel, navController: NavController, veterinarioId: Int?) {
    val isEditing = veterinarioId != null
    val veterinario = if (isEditing) viewModel.veterinarios.value.find { it.id == veterinarioId } else null

    var nombre by remember { mutableStateOf(veterinario?.nombre ?: "") }
    var especialidad by remember { mutableStateOf(veterinario?.especialidad ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar Veterinario" else "Nuevo Veterinario") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = especialidad,
                onValueChange = { especialidad = it },
                label = { Text("Especialidad") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val veterinarioToSave = Veterinario(
                        id = veterinarioId ?: 0, // El ID se actualiza en el ViewModel
                        nombre = nombre,
                        especialidad = especialidad
                    )
                    if (isEditing) {
                        viewModel.updateVeterinario(veterinarioToSave)
                    } else {
                        viewModel.addVeterinario(veterinarioToSave)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Guardar")
            }
        }
    }
}
