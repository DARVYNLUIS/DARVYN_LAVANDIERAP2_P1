package edu.ucne.darvyn_lavandierap2_p1.presentation.huacal.edit

data class EditHuacalUiState(
    val idEntrada: Int? = null,
    val nombreCliente: String = "",
    val fecha: String = "",
    val cantidad: String = "",
    val precio: String = "",

    val nombreError: String? = null,
    val fechaError: String? = null,
    val cantidadError: String? = null,
    val precioError: String? = null,

    val isNew: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val message: String? = null
)
