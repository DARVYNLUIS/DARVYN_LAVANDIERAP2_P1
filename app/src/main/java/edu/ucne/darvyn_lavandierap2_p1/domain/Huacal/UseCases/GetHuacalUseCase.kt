package edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases


import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.repository.HuacalRepository
import javax.inject.Inject

class GetEntradaHuacalUseCase @Inject constructor(
    private val repository: HuacalRepository
) {
    suspend operator fun invoke(id: Int): EntradaHuacal? = repository.getEntrada(id)
}
