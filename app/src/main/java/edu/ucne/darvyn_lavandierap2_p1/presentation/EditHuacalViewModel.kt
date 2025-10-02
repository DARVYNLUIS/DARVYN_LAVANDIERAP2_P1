package edu.ucne.darvyn_lavandierap2_p1.presentation.huacal.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal
import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.UseCases.UpsertHuacalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditHuacalViewModel @Inject constructor(
    private val upsertHuacalUseCase: UpsertHuacalUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditHuacalUiState())
    val state: StateFlow<EditHuacalUiState> = _state.asStateFlow()

    fun onEvent(event: EditHuacalUiEvent) {
        when (event) {
            is EditHuacalUiEvent.NombreClienteChanged ->
                _state.update { it.copy(nombreCliente = event.nombre, nombreError = null) }

            is EditHuacalUiEvent.FechaChanged ->
                _state.update { it.copy(fecha = event.fecha, fechaError = null) }

            is EditHuacalUiEvent.CantidadChanged ->
                _state.update { it.copy(cantidad = event.cantidad, cantidadError = null) }

            is EditHuacalUiEvent.PrecioChanged ->
                _state.update { it.copy(precio = event.precio, precioError = null) }

            is EditHuacalUiEvent.Load ->
                loadHuacal(event.entradaId)

            EditHuacalUiEvent.Save ->
                saveHuacal()
        }
    }

    private fun loadHuacal(id: Int) {
        viewModelScope.launch {
            if (id == 0) {
                // Inicializar con fecha de hoy
                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                _state.update { it.copy(isNew = true, idEntrada = null, fecha = today) }
            } else {
                val entrada = upsertHuacalUseCase.getEntradaById(id)
                entrada?.let {
                    _state.update {
                        it.copy(
                            isNew = false,
                            idEntrada = entrada.idEntrada,
                            nombreCliente = entrada.nombreCliente,
                            fecha = entrada.fecha,
                            cantidad = entrada.cantidad.toString(),
                            precio = entrada.precio.toString()
                        )
                    }
                }
            }
        }
    }

    private fun saveHuacal() {
        val current = state.value

        // Validaciones
        val nombreError = if (current.nombreCliente.isBlank()) "El cliente es obligatorio" else null
        val fechaError = if (current.fecha.isBlank()) "La fecha es obligatoria" else null
        val cantidadError = if (current.cantidad.isBlank()) "La cantidad es obligatoria" else null
        val precioError = if (current.precio.isBlank()) "El precio es obligatorio" else null

        if (listOf(nombreError, fechaError, cantidadError, precioError).any { it != null }) {
            _state.update {
                it.copy(
                    nombreError = nombreError,
                    fechaError = fechaError,
                    cantidadError = cantidadError,
                    precioError = precioError
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val entrada = EntradaHuacal(
                idEntrada = current.idEntrada ?: 0,
                nombreCliente = current.nombreCliente,
                fecha = current.fecha,
                cantidad = current.cantidad.toIntOrNull() ?: 0,
                precio = current.precio.toDoubleOrNull() ?: 0.0
            )

            val result = upsertHuacalUseCase(entrada)
            result.fold(
                onSuccess = {
                    _state.update {
                        it.copy(isSaving = false, isSaved = true, message = "Huacal guardado correctamente")
                    }
                },
                onFailure = { e ->
                    _state.update { it.copy(isSaving = false, message = "Error: ${e.message}") }
                }
            )
        }
    }

    fun clearMessage() {
        _state.update { it.copy(message = null) }
    }
}
