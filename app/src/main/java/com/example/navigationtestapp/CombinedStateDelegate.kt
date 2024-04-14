package com.example.navigationtestapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow


/**
 * Объединяет UiStateDelegate и InternalStateDelegate, позволяя связать логику этих двух состояний.
 * Это особенно полезно, когда нам нужно обновить UI-состояние, зависящее от внутреннего состояния.
 */
interface CombinedStateDelegate<UiState, State, Event> :
    UiStateDelegate<UiState, Event>,
    InternalStateDelegate<State> {


        suspend fun CombinedStateDelegate<UiState, State, Event>.updateUiState(
        transform: (uiState: UiState, state: State) -> UiState
    )


    fun CombinedStateDelegate<UiState, State, Event>.collectUpdateUiState(
        coroutineScope: CoroutineScope,
        transform: (state: State, uiState: UiState) -> UiState,
    ): Job


    fun <T> CombinedStateDelegate<UiState, State, Event>.combineCollectUpdateUiState(
        coroutineScope: CoroutineScope,
        flow: Flow<T>,
        transform: suspend (state: State, uiState: UiState, value: T) -> UiState,
    ): Job

    fun <T1, T2> CombinedStateDelegate<UiState, State, Event>.combineCollectUpdateUiState(
        coroutineScope: CoroutineScope,
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        transform: suspend (state: State, uiState: UiState, value1: T1, value2: T2) -> UiState,
    ): Job

    fun <T1, T2, T3> CombinedStateDelegate<UiState, State, Event>.combineCollectUpdateUiState(
        coroutineScope: CoroutineScope,
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        transform: suspend (state: State, uiState: UiState, value1: T1, value2: T2, value3: T3) -> UiState,
    ): Job

    fun <T1, T2, T3, T4> CombinedStateDelegate<UiState, State, Event>.combineCollectUpdateUiState(
        coroutineScope: CoroutineScope,
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        transform: suspend (state: State, uiState: UiState, value1: T1, value2: T2, value3: T3, value4: T4) -> UiState,
    ): Job
}