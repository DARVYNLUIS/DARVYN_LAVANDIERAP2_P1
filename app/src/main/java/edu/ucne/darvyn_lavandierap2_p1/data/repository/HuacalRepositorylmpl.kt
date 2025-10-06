package edu.ucne.darvyn_lavandierap2_p1.data.repository

import edu.ucne.darvyn_lavandierap2_p1.data.local.dao.HuacalesDao
import edu.ucne.darvyn_lavandierap2_p1.data.local.mapper.HuacalMapper
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.repository.HuacalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HuacalRepositoryImpl @Inject constructor(
    private val dao: HuacalesDao
): HuacalRepository {

    override suspend fun upsert(entrada: EntradaHuacal): Int {
        return dao.insert(HuacalMapper.toEntity(entrada)).toInt()
    }

    override suspend fun delete(id: Int) {
        dao.findById(id)?.let { dao.delete(it) }
    }

    override suspend fun getEntrada(id: Int): EntradaHuacal? {
        return dao.findById(id)?.let { HuacalMapper.toDomain(it) }
    }

    override fun observeEntradas(
        cliente: String?,
        fecha: String?,
        cantidad: Int?
    ): Flow<List<EntradaHuacal>> {
        return dao.getEntradas(cliente, fecha, cantidad).map { list ->
            list.map { HuacalMapper.toDomain(it) }
        }
    }


    override suspend fun existePorClienteYCantidad(nombreCliente: String, fecha: String): Boolean {
        val entradas = dao.getEntradas(nombreCliente, fecha)
            .map { it.map { e -> HuacalMapper.toDomain(e) } }
        return entradas.first().isNotEmpty()
    }
}
