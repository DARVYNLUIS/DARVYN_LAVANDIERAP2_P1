package edu.ucne.darvyn_lavandierap2_p1.presentation.huacal.edit

sealed class EditHuacalUiEvent {
    data class NombreClienteChanged(val nombre: String) : EditHuacalUiEvent()
    data class FechaChanged(val fecha: String) : EditHuacalUiEvent()
    data class CantidadChanged(val cantidad: String) : EditHuacalUiEvent()
    data class PrecioChanged(val precio: String) : EditHuacalUiEvent()
    data class Load(val entradaId: Int) : EditHuacalUiEvent()
    object Save : EditHuacalUiEvent()
}
