package edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases

import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.repository.HuacalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class observeHuacalUseCase @Inject constructor(
    private val repository: HuacalRepository
) {
    operator fun invoke(
        cliente: String? = null,
        cantidad: Int? = null
    ): Flow<List<EntradaHuacal>> {
        return repository.observeEntradas(cliente = cliente, cantidad = cantidad)
    }
}
