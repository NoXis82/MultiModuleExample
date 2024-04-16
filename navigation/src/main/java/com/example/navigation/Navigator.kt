package com.example.navigation

import androidx.navigation.NavOptionsBuilder
import com.example.common_core.navigation.NavigationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(): NavigationService {

    private val _actions = MutableSharedFlow<Action>(
        replay = 0,
        extraBufferCapacity = 10
    )
    val actions: Flow<Action> = _actions.asSharedFlow()
    override fun navigateTo(destination: String, navOptions: NavOptionsBuilder.() -> Unit) {
        _actions.tryEmit(
            Action.Navigate(destination, navOptions)
        )
    }

    override fun goBack() {
        _actions.tryEmit(Action.Back)
    }

}