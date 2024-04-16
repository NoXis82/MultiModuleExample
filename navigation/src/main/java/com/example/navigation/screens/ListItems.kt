package com.example.navigation.screens

import com.example.navigation.utils.WithoutArgsScreen


/**
 * Без аргументов
 */
object ListItems: WithoutArgsScreen() {
    override val route: String
        get() = "listItems"

}
