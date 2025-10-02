package edu.ucne.darvyn_lavandierap2_p1.presentation.ListHuacal


import edu.ucne.darvyn_lavandierap2_p1.domain.Huacal.model.EntradaHuacal

data class ListHuacalUiState(
    val entradas: List<EntradaHuacal> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)
