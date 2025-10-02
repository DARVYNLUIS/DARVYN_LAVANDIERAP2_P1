package edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model

data class EntradaHuacal(
    val idEntrada: Int = 0,
    val fecha: String,
    val nombreCliente: String,
    val cantidad: Int,
    val precio: Double
)

