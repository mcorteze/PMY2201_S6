package com.example.veterinaria.data.model

import java.time.LocalDate

data class Consulta(
    val id: Int,
    val mascotaId: Int,
    val duenoId: String,
    val descripcion: String,
    val costoBase: Double,
    val fecha: LocalDate
)
