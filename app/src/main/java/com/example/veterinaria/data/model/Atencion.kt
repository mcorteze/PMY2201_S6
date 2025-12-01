package com.example.veterinaria.data.model

class Atencion(
    val dueño: Dueño,
    val mascota: Mascota,
    val consulta: Consulta
) {
    fun calcularTotal(aplicarPromo: Boolean): Double {
        val d1 = consulta.calcularDescuento()
        val d2 = consulta.calcularDescuentoPromocion(aplicarPromo)
        return consulta.costoBase - d1 - d2
    }

    fun mostrarResumen(disponible: Boolean, aplicarPromo: Boolean) {
        val estado = consulta.verificarDisponibilidad(disponible)
        val total = calcularTotal(aplicarPromo)

        println("----- RESUMEN DE CONSULTA -----")
        println("Dueño: ${dueño.nombre}")
        println("Teléfono: ${dueño.telefono} | Correo: ${dueño.email}")
        println("Mascota: ${mascota.nombre} (${mascota.especie})")
        println("Edad: ${mascota.edad} años | Peso: ${mascota.peso} kg")
        println("Fecha de consulta: ${consulta.fecha}")
        println("Motivo: ${consulta.descripcion}")
        println("Costo base: ${consulta.costoBase}")
        if (consulta.cantidadMascotas > 1) println("Se aplicó 15% de descuento por múltiples mascotas.")
        if (aplicarPromo) println("Se aplicó 10% adicional por promoción vigente.")
        println("Total a pagar: %.2f".format(total))
        println("Estado de la consulta: $estado")
        println("----------------------------------")
    }
}
