package com.example.veterinaria.data.model

data class Mascota(
    val id: Int,
    var duenoId: String, // ID del dueño (RUT)
    val nombre: String,
    val especie: String,
    val edad: Int,
    val peso: Double
)
