package com.example.navigationtestapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

/**
 * Предоставляет возможность управлять внутренним состоянием ViewModel
 */
interface InternalStateDelegate<State> {

    val InternalStateDelegate<State>.internalStateFlow: Flow<State>

    val InternalStateDelegate<State>.internalState: State

    suspend fun InternalStateDelegate<State>.updateInternalState(
        transform: (state: State) -> State,
    )

    fun InternalStateDelegate<State>.asyncUpdateInternalState(
        coroutineScope: CoroutineScope, transform: (state: State) -> State
    ): Job

}