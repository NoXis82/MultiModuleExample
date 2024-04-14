package com.example.navigationtestapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


/**
 * Реализация делегата для управления состоянием.
 * Этот делегат хранит и управляет внутренним состоянием.
 *
 * @param mutexState - мьютекс для синхронизации доступа к состоянию.
 * @param initialState - начальное внутреннее состояние.
 */
class InternalStateDelegateImpl<State>(
    initialState: State,
    private val mutexState: Mutex = Mutex()
) : InternalStateDelegate<State> {

    private val internalMutableState = MutableStateFlow(initialState)

    override val InternalStateDelegate<State>.internalStateFlow: Flow<State>
        get() = internalMutableState.asStateFlow()

    override val InternalStateDelegate<State>.internalState: State
        get() = internalMutableState.value

    override suspend fun InternalStateDelegate<State>.updateInternalState(transform: (state: State) -> State) {
        mutexState.withLock {
            internalMutableState.update(transform)
        }
    }

    override fun InternalStateDelegate<State>.asyncUpdateInternalState(
        coroutineScope: CoroutineScope,
        transform: (state: State) -> State
    ): Job {
        return coroutineScope.launch {
            updateInternalState(transform)
        }
    }
}