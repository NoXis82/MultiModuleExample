package com.example.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.screens.Detail
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigation(
    navigator: Navigator,
    listScreen: @Composable () -> Unit,
    detailScreen: @Composable (String) -> Unit,
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        navigator.actions.collectLatest { action ->
            when(action) {
                Action.Back -> navController.popBackStack()
                is Action.Navigate -> navController.navigate(
                    route = action.destination,
                    builder = action.navOptions
                )
            }
        }
    }

    NavHost(navController, startDestination = Destination.list.route) {
        composable(Destination.list.route) {
            listScreen.invoke()
        }
        composable(Destination.detail.route, Destination.detail.arguments) {
            val product = Detail.objectParser(it)

            detailScreen(product)
        }
    }
}