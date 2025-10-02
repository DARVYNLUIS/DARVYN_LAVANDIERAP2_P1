package edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases


import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.repository.HuacalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class observeHuacalUseCase @Inject constructor(
    private val repository: HuacalRepository
) {
    operator fun invoke(cliente: String? = null, fecha: String? = null): Flow<List<EntradaHuacal>> =
        repository.observeEntradas(cliente, fecha)
}
