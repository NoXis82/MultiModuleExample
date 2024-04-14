package com.example.navigationtestapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Этот интерфейс, который позволяет нам управлять UI-состоянием в ViewModel
 * С его помощью вы можете легко обновлять UI-состояние и отправлять события, связанные с UI
 */
interface UiStateDelegate<UiState, Event> {
    val uiStateFlow: StateFlow<UiState>

    //используется для передачи events
    val singleEvents: Flow<Event>

    val UiStateDelegate<UiState, Event>.uiState: UiState

    //используются для обновления состояния UI (UiState)
    //асинхронно, чтобы не блокировать основной поток
    suspend fun UiStateDelegate<UiState, Event>.updateUiState(
        transform: (uiState: UiState) -> UiState,
    )

    //используются для обновления состояния UI (UiState)
    //асинхронно, чтобы не блокировать основной поток
    fun UiStateDelegate<UiState, Event>.asyncUpdateUiState(
        coroutineScope: CoroutineScope,
        transform: (state: UiState) -> UiState,
    ): Job

    //используются для отправки события
    suspend fun UiStateDelegate<UiState, Event>.sendEvent(event: Event)

}