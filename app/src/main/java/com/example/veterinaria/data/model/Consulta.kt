package com.example.veterinaria.data.model

import java.time.LocalDate

data class Consulta(
    val id: Int,
    val descripcion: String,
    val costoBase: Double,
    val cantidadMascotas: Int,
    val fecha: LocalDate
) {
    // Calcula descuento por cantidad de mascotas
    fun calcularDescuento(): Double {
        val descuento = if (cantidadMascotas > 1) 0.15 else 0.0
        return costoBase * descuento
    }

    // Calcula descuento adicional por promoción
    fun calcularDescuentoPromocion(aplicarPromo: Boolean): Double {
        val porcentaje = if (aplicarPromo) 0.10 else 0.0
        return costoBase * porcentaje
    }

    // Verifica si el veterinario está disponible
    fun verificarDisponibilidad(disponible: Boolean): String {
        return if (disponible) "Programada" else "Pendiente"
    }

    // Combina dos consultas sumando costos y cantidades
    operator fun plus(otra: Consulta): Consulta {
        val nuevoCosto = this.costoBase + otra.costoBase
        val nuevaCantidad = this.cantidadMascotas + otra.cantidadMascotas
        return Consulta(
            id = this.id,
            descripcion = this.descripcion + " + " + otra.descripcion,
            costoBase = nuevoCosto,
            cantidadMascotas = nuevaCantidad,
            fecha = if (this.fecha.isBefore(otra.fecha)) this.fecha else otra.fecha
        )
    }
}
