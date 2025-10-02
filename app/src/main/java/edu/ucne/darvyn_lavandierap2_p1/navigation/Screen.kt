package edu.ucne.darvyn_lavandierap2_p1.navigation



sealed class HuacalScreen(val route: String) {
    object HuacalList : HuacalScreen("huacal_list")
    object EditHuacal : HuacalScreen("edit_huacal/{entradaId}") {
        fun createRoute(entradaId: Int?) = "edit_huacal/${entradaId ?: -1}"
    }
}

