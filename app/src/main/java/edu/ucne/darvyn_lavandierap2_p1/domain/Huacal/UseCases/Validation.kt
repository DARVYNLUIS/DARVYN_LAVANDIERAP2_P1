package edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases

data class ValidationResultHuacal(
    val isValid: Boolean,
    val nombreClienteError: String? = null,
    val fechaError: String? = null,
    val cantidadError: String? = null,
    val precioError: String? = null
)

fun validateEntradaHuacal(
    nombreCliente: String,
    fecha: String,
    cantidad: Int,
    precio: Double
): ValidationResultHuacal {
    if(nombreCliente.isBlank()) return ValidationResultHuacal(false, nombreClienteError = "El nombre del cliente es requerido")
    if(fecha.isBlank()) return ValidationResultHuacal(false, fechaError = "La fecha es requerida")
    if(cantidad <= 0) return ValidationResultHuacal(false, cantidadError = "La cantidad debe ser mayor a 0")
    if(precio <= 0.0) return ValidationResultHuacal(false, precioError = "El precio debe ser mayor a 0")
    return ValidationResultHuacal(true)
}
