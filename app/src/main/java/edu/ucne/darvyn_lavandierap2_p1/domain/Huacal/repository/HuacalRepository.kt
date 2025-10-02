package edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.repository

import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal
import kotlinx.coroutines.flow.Flow

interface HuacalRepository {

    suspend fun upsert(entrada: EntradaHuacal): Int
    suspend fun delete(id: Int)
    suspend fun getEntrada(id: Int): EntradaHuacal?

    fun observeEntradas(cliente: String? = null, fecha: String? = null): Flow<List<EntradaHuacal>>

    suspend fun existePorClienteYCantidad(nombreCliente: String, fecha: String): Boolean
}

