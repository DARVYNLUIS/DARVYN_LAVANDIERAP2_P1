package edu.ucne.darvyn_lavandierap2_p1.presentation.ListHuacal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases.deleteHuacalUseCase
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases.observeHuacalUseCase
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListHuacalViewModel @Inject constructor(
    private val observeHuacalUseCase: observeHuacalUseCase,
    private val deleteHuacalUseCase: deleteHuacalUseCase
) : ViewModel() {

    private val _clienteFilter = MutableStateFlow<String?>(null)
    private val _cantidadFilter = MutableStateFlow<Int?>(null)

    private val _state = MutableStateFlow(ListHuacalUiState())
    val state: StateFlow<ListHuacalUiState> = _state.asStateFlow()

    init {
        loadEntradas()
    }

    private fun loadEntradas() {
        viewModelScope.launch {
            combine(_clienteFilter, _cantidadFilter) { cliente, cantidad ->
                cliente to cantidad
            }.flatMapLatest { (cliente, cantidad) ->
                observeHuacalUseCase(cliente, cantidad)
            }.collect { entradas ->
                _state.update { it.copy(entradas = entradas) }
            }
        }
    }

    fun onClienteFilterChanged(value: String) {
        val cliente = value.ifBlank { null }
        _clienteFilter.value = cliente
        _state.update { it.copy(clienteFilter = cliente) }
    }

    fun onCantidadFilterChanged(value: String) {
        val cantidad = value.toIntOrNull()
        _cantidadFilter.value = cantidad
        _state.update { it.copy(cantidadFilter = cantidad) }
    }

    fun deleteEntrada(id: Int, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                deleteHuacalUseCase(id)
                onResult("Entrada eliminada correctamente")
            } catch (e: Exception) {
                onResult("Error al eliminar: ${e.message}")
            }
        }
    }
}
