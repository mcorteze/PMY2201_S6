package com.example.veterinaria.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onNavigateTo: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("Veterinaria Animales Fantásticos") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menú")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Inicio") },
                    onClick = {
                        expanded = false
                        onNavigateTo("home")
                    },
                    leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                DropdownMenuItem(
                    text = { Text("Mascotas") },
                    onClick = {
                        expanded = false
                        onNavigateTo("mascotas")
                    },
                    leadingIcon = { Icon(Icons.Default.Pets, contentDescription = null) }
                )
                DropdownMenuItem(
                    text = { Text("Dueños") },
                    onClick = {
                        expanded = false
                        onNavigateTo("duenos")
                    },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
                )
                DropdownMenuItem(
                    text = { Text("Consultas") },
                    onClick = {
                        expanded = false
                        onNavigateTo("consultas")
                    },
                    leadingIcon = { Icon(Icons.Default.MedicalServices, contentDescription = null) }
                )
            }
        }
    )
}
