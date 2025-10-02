package edu.ucne.darvyn_lavandierap2_p1.presentation.ListHuacal


sealed class ListHuacalUiEvent {
    data class Delete(val entradaId: Int) : ListHuacalUiEvent()
}
