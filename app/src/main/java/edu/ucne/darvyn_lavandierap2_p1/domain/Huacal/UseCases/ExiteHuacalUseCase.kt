package edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases


import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.repository.HuacalRepository
import javax.inject.Inject

class ExisteEntradaUseCase @Inject constructor(
    private val repository: HuacalRepository
) {
    suspend operator fun invoke(nombreCliente: String, fecha: String): Boolean {
        return repository.existePorClienteYCantidad(nombreCliente, fecha)
    }
}
