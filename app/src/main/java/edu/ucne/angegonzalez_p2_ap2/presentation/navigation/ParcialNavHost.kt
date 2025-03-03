package edu.ucne.angegonzalez_p2_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.angegonzalez_p2_ap2.presentation.deposito.DepositoListScreen
import edu.ucne.angegonzalez_p2_ap2.presentation.deposito.DepositoScreen

@Composable
fun ParcialNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.DepositoListScreen
    ) {
        composable<Screen.DepositoListScreen> {
            DepositoListScreen(
                createDeposito = { navHostController.navigate(Screen.DepositoScreen(0)) },
                goToMenu = {navHostController.navigateUp()},
                goToDeposito = { navHostController.navigate(Screen.DepositoScreen(it)) }
            )
        }
        composable<Screen.DepositoScreen> {
            val depositoId = it.toRoute<Screen.DepositoScreen>().depositoId
            DepositoScreen(
                depositoId = depositoId,
                goBackToList = { navHostController.navigateUp() }
            )
        }
    }
}