package com.example.navigation.utils

import androidx.navigation.NavBackStackEntry

typealias DestinationRoute = String
interface NavDestination<Arg> {
    fun destination(arg: Arg): DestinationRoute
    fun objectParser(entry: NavBackStackEntry): Arg

}