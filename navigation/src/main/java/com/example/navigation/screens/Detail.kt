package com.example.navigation.screens

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.navigation.utils.ArgsScreen
import com.example.navigation.utils.DestinationRoute

object Detail : ArgsScreen<String> {
    override fun destination(arg: String): DestinationRoute {
        return "detail/$arg"
    }

    override val route: String
        get() = "detail/{product}"
    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument("product") {
            type = NavType.StringType
        })

    override fun objectParser(entry: NavBackStackEntry): String {
        return entry.arguments?.getString("product")!!
    }

}