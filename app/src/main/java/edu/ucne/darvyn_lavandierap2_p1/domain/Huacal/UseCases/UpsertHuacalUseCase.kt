package edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases

import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.repository.HuacalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpsertHuacalUseCase @Inject constructor(
    private val repository: HuacalRepository
) {
    suspend operator fun invoke(entrada: EntradaHuacal): Result<Int> {
        val existing = repository.observeEntradas().first()
        val duplicate = existing.any {
            it.nombreCliente.equals(entrada.nombreCliente, ignoreCase = true) &&
                    it.idEntrada != entrada.idEntrada &&
                    it.fecha == entrada.fecha
        }
        if (duplicate) {
            return Result.failure(
                IllegalArgumentException("Ya existe una entrada para este cliente y fecha")
            )
        }

        return runCatching { repository.upsert(entrada) }
    }

    suspend fun deleteEntrada(id: Int) = repository.delete(id)

    suspend fun getEntradaById(id: Int): EntradaHuacal? = repository.getEntrada(id)

    fun observeEntradas(): Flow<List<EntradaHuacal>> = repository.observeEntradas()
}
