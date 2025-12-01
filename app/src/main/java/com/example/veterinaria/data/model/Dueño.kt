package com.example.veterinaria.data.model

data class Dueño(
    val id: String, // Usaremos el RUT como ID único
    val nombre: String,
    val telefono: String,
    val email: String
)
