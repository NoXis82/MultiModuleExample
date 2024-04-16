package com.example.navigation

import androidx.navigation.NavOptionsBuilder
import com.example.navigation.utils.DestinationRoute

sealed class Action {

    data class Navigate(
        val destination: DestinationRoute,
        val navOptions: NavOptionsBuilder.() -> Unit
    ) : Action()

    data object Back : Action()

}
