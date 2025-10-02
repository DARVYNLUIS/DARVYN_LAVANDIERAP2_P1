package edu.ucne.darvyn_lavandierap2_p1.presentation.huacal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import edu.ucne.darvyn_lavandierap2_p1.navigation.HuacalScreen
import edu.ucne.darvyn_lavandierap2_p1.presentation.HuacalEditScreen
import edu.ucne.darvyn_lavandierap2_p1.presentation.ListHuacal.ListHuacalScreen


@Composable
fun HuacalNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HuacalScreen.HuacalList.route,
        modifier = modifier
    ) {
        composable(HuacalScreen.HuacalList.route) {
            ListHuacalScreen(
                onNavigateToEdit = { entradaId ->
                    navController.navigate(HuacalScreen.EditHuacal.createRoute(entradaId))
                },
                onNavigateToCreate = {
                    navController.navigate(HuacalScreen.EditHuacal.createRoute(null))
                }
            )
        }

        composable(
            route = "edit_huacal/{entradaId}",
            arguments = listOf(navArgument("entradaId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val entradaId = backStackEntry.arguments?.getInt("entradaId")?.takeIf { it != -1 }
            HuacalEditScreen(
                entradaId = entradaId,
                navController = navController
            )
        }
    }
}
