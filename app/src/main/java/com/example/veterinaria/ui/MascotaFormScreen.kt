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
import com.example.veterinaria.data.model.Mascota
import com.example.veterinaria.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MascotaFormScreen(viewModel: MainViewModel, navController: NavController, mascotaId: Int?) {
    val isEditing = mascotaId != null
    val mascota = if (isEditing) viewModel.mascotas.value.find { it.id == mascotaId } else null

    var nombre by remember { mutableStateOf(mascota?.nombre ?: "") }
    var especie by remember { mutableStateOf(mascota?.especie ?: "") }
    var edad by remember { mutableStateOf(mascota?.edad?.toString() ?: "") }
    var peso by remember { mutableStateOf(mascota?.peso?.toString() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar Mascota" else "Nueva Mascota") },
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
                value = especie,
                onValueChange = { especie = it },
                label = { Text("Especie") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val mascotaToSave = Mascota(
                        id = mascotaId ?: 0, // El ID se actualiza en el ViewModel
                        nombre = nombre,
                        especie = especie,
                        edad = edad.toIntOrNull() ?: 0,
                        peso = peso.toDoubleOrNull() ?: 0.0
                    )
                    if (isEditing) {
                        viewModel.updateMascota(mascotaToSave)
                    } else {
                        viewModel.addMascota(mascotaToSave)
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
