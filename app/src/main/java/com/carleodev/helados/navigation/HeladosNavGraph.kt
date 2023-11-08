package com.carleodev.helados.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.carleodev.helados.ui.home.AnimatedSplashScreen
import com.carleodev.helados.ui.home.CrearItemDestination
import com.carleodev.helados.ui.home.CrearItemScreen
import com.carleodev.helados.ui.home.ListarItemDestination
import com.carleodev.helados.ui.home.ListarItemScreen
import com.carleodev.helados.ui.home.SplashDestino


@Composable
fun HeladosNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = SplashDestino.route,
        modifier = modifier
    ) {

        composable(route = SplashDestino.route) {
            AnimatedSplashScreen(
                navController = navController
            )
        }



        composable(route = CrearItemDestination.routeWithArgs,
                arguments = listOf(navArgument(CrearItemDestination.itemIdArg) {
            type = NavType.IntType })
        ) {
            CrearItemScreen(
                onNavigateUp = { navController.navigateUp() },
            )
        }

        composable(route = ListarItemDestination.route) {
            ListarItemScreen(
                onNavigateUp = { navController.navigateUp() },
                navigateToEditItem = {navController.navigate("${CrearItemDestination.route}/${it}")}

            )
        }


    }
}