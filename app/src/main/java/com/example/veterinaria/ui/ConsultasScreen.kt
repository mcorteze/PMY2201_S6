package com.example.veterinaria.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.veterinaria.data.model.Consulta
import com.example.veterinaria.viewmodel.MainViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultasScreen(viewModel: MainViewModel) {
    val consultas by viewModel.consultas.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var consultaToEdit by remember { mutableStateOf<Consulta?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                consultaToEdit = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Consulta")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Text("Gestión de Consultas", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))
            }
            items(consultas) { consulta ->
                ConsultaCard(
                    consulta = consulta,
                    onEdit = {
                        consultaToEdit = it
                        showDialog = true
                    },
                    onDelete = {
                        viewModel.deleteConsulta(it)
                    }
                )
            }
        }
    }

    if (showDialog) {
        ConsultaFormDialog(
            consulta = consultaToEdit,
            onDismiss = { showDialog = false },
            onSave = {
                if (consultaToEdit == null) {
                    viewModel.addConsulta(it)
                } else {
                    viewModel.updateConsulta(it)
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun ConsultaCard(consulta: Consulta, onEdit: (Consulta) -> Unit, onDelete: (Consulta) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(consulta.descripcion, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Costo: $${consulta.costoBase}")
                Text("Fecha: ${consulta.fecha}")
            }
            Spacer(Modifier.width(16.dp))
            IconButton(onClick = { onEdit(consulta) }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = { onDelete(consulta) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaFormDialog(
    consulta: Consulta?,
    onDismiss: () -> Unit,
    onSave: (Consulta) -> Unit
) {
    var descripcion by remember { mutableStateOf(consulta?.descripcion ?: "") }
    var costoBase by remember { mutableStateOf(consulta?.costoBase?.toString() ?: "") }
    var cantidadMascotas by remember { mutableStateOf(consulta?.cantidadMascotas?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (consulta == null) "Nueva Consulta" else "Editar Consulta") },
        text = {
            Column {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = costoBase,
                    onValueChange = { costoBase = it },
                    label = { Text("Costo Base") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = cantidadMascotas,
                    onValueChange = { cantidadMascotas = it },
                    label = { Text("Cantidad de Mascotas") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val newConsulta = Consulta(
                    id = consulta?.id ?: 0, // El ID se asignará en el ViewModel
                    descripcion = descripcion,
                    costoBase = costoBase.toDoubleOrNull() ?: 0.0,
                    cantidadMascotas = cantidadMascotas.toIntOrNull() ?: 0,
                    fecha = LocalDate.now()
                )
                onSave(newConsulta)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
