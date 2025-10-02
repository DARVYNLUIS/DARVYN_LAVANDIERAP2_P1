package edu.ucne.darvyn_lavandierap2_p1.presentation.ListHuacal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases.deleteHuacalUseCase
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases.observeHuacalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListHuacalViewModel @Inject constructor(
    private val observeHuacalUseCase: observeHuacalUseCase,
    private val deleteHuacalUseCase: deleteHuacalUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListHuacalUiState())
    val state: StateFlow<ListHuacalUiState> = _state.asStateFlow()

    init {
        loadEntradas()
    }

    private fun loadEntradas() {
        viewModelScope.launch {
            observeHuacalUseCase().collect { entradas ->
                _state.update { it.copy(entradas = entradas) }
            }
        }
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
