README

Autor: Manuel Cortez
Versión de Kotlin: 2.2.20
Fecha: Noviembre 2025

Descripción General
El proyecto 'Veterinaria Animales Fantásticos' es una aplicación desarrollada en Kotlin que simula la gestión de una veterinaria. Permite registrar dueños, mascotas y consultas, aplicar descuentos y promociones, y mostrar un resumen completo de las operaciones. Implementa conceptos avanzados de Kotlin como expresiones regulares, rangos, anotaciones personalizadas, reflexión, sobrecarga de operadores, desestructuración de objetos y comparación de igualdad.

Requisitos de Ejecución
- Kotlin versión 2.2.20
- IntelliJ IDEA Community Edition o similar
- Librerías incluidas:
  * kotlin-stdlib-2.2.20.jar
  * kotlin-reflect-2.2.20.jar

Ejecución del Programa
1. Abrir el proyecto en IntelliJ IDEA.
2. Ejecutar el archivo Main.kt ubicado en src/
3. Ingresar los datos solicitados por consola (nombre, correo, teléfono, mascota, etc.).
4. El programa mostrará un resumen detallado de la consulta, descuentos aplicados, promociones activas, y resultados de comparación y desestructuración.

Estructura del Proyecto
src/
         ├── Main.kt
         ├── model/
         │    ├── Atencion.kt
         │    ├── Consulta.kt
         │    ├── Dueño.kt
         │    ├── Mascota.kt
         │    ├── Servicio.kt
         │    ├── Promocionable.kt
         └── util/
              └── Validaciones.kt

Funciones Implementadas
- Validación de correo electrónico y teléfono (Regex).
- Validación de rangos de fechas y cantidades (Ranges).
- Anotación personalizada @Promocionable.
- Uso de reflexión para listar propiedades y métodos.
- Sobrecarga de operadores (+ y ==).
- Desestructuración de objetos.
- Sobrescritura de equals() y hashCode().
- Generación de resumen integrado en consola.
