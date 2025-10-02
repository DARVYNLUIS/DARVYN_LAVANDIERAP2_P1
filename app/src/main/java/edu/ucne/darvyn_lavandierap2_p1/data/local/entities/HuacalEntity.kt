package edu.ucne.darvyn_lavandierap2_p1.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "EntradasHuacales")
data class HuacalEntity(
    @PrimaryKey(autoGenerate = true)
    val idEntrada: Int = 0,
    val fecha: String,
    val nombreCliente: String,
    val cantidad: Int,
    val precio: Double
)

