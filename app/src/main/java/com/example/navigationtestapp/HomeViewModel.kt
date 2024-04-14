package com.example.navigationtestapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dashboardRepository: DashboardRepository
) : ViewModel(), CombinedStateDelegate<UiState, State, Event> by CombinedStateDelegateImpl(
    initialState = State(),
    initialUiState = UiState(),
) {

    init {
        collectUpdateUiState(viewModelScope) { state, uiState ->
            val newItems = if (uiState.filter.isBlank()) {
                state.fullItems
            } else {
                state.fullItems.filter { item -> item.title.contains(uiState.filter) }
            }
            uiState.copy(items = newItems)
        }
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.stackTrace
        }) {
            updateUiState { uiState, _ -> uiState.copy(isLoading = true) }
            val items = runCatching {
                dashboardRepository.getHomeItems()
            }.getOrDefault(emptyList())

            val uiItems = items.map { item -> item.toDashboardUi() }

            updateInternalState { state ->
                state.copy(fullItems = uiItems)
            }

        }.invokeOnCompletion {
            asyncUpdateUiState(viewModelScope) { uiState -> uiState.copy(isLoading = false) }
        }
    }

    fun onForgotPasswordClick() {
        viewModelScope.launch { sendEvent(Event.StartForgotPasswordFeature) }
    }

    fun onUserClick() {
        viewModelScope.launch { sendEvent(Event.UserFeature) }
    }

}


@Composable
fun <State, Event> UiStateDelegate<State, Event>.CollectEventEffect(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    vararg keys: Any?,
    collector: FlowCollector<Event>,
) = LaunchedEffect(Unit, *keys) {
    singleEvents.flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = lifecycleState,
    ).collect(collector)
}
