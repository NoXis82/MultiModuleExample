package com.example.navigationtestapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

/**
 * Реализация делегата для управления состоянием.
 * Этот делегат хранит и управляет двумя типами состояний: состояние пользовательского интерфейса и внутреннее состояние.
 *
 * @param mutexState - мьютекс для синхронизации доступа к состоянию.
 * @param initialUiState - начальное состояние пользовательского интерфейса.
 * @param initialState - начальное внутреннее состояние.
 * @param singleLiveEventCapacity - емкость канала для SingleLiveEvent.
 */
class CombinedStateDelegateImpl<UiState, State, Event>(
    private val mutexState: Mutex = Mutex(),
    initialUiState: UiState,
    initialState: State,
    singleLiveEventCapacity: Int = Channel.BUFFERED,
) : CombinedStateDelegate<UiState, State, Event>,
    UiStateDelegate<UiState, Event> by UIStateDelegateImpl(
        mutexState = mutexState,
        initialUiState = initialUiState,
        singleLiveEventCapacity = singleLiveEventCapacity,
    ),
    InternalStateDelegate<State> by InternalStateDelegateImpl(
        initialState, mutexState
    ) {

    override suspend fun CombinedStateDelegate<UiState, State, Event>.updateUiState(transform: (uiState: UiState, state: State) -> UiState) {
        updateUiState { uiState -> transform(uiState, internalState) }
    }

    /**
     * Подписка на изменения внутреннего состояния с трансформацией
     * состояния пользовательского интерфейса в зависимости от изменений внутреннего состояния.
     */
    override fun CombinedStateDelegate<UiState, State, Event>.collectUpdateUiState(
        coroutineScope: CoroutineScope,
        transform: (state: State, uiState: UiState) -> UiState
    ): Job {
        return internalStateFlow.onEach { state ->
            updateUiState { uiState -> transform(state, uiState) }
        }.launchIn(coroutineScope)
    }

    override fun <T> CombinedStateDelegate<UiState, State, Event>.combineCollectUpdateUiState(
        coroutineScope: CoroutineScope,
        flow: Flow<T>,
        transform: suspend (state: State, uiState: UiState, value: T) -> UiState
    ): Job {
        return internalStateFlow.combine(flow) { state, value -> transform(state, uiState, value) }
            .onEach { newState -> updateUiState { _ -> newState } }
            .launchIn(coroutineScope)
    }

    override fun <T1, T2> CombinedStateDelegate<UiState, State, Event>.combineCollectUpdateUiState(
        coroutineScope: CoroutineScope,
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        transform: suspend (state: State, uiState: UiState, value1: T1, value2: T2) -> UiState
    ): Job {
        return combine(internalStateFlow, flow1, flow2) { state, value1, value2 ->
            transform(
                state,
                uiState,
                value1,
                value2
            )
        }.onEach { newState -> updateUiState { _ -> newState } }
            .launchIn(coroutineScope)
    }

    override fun <T1, T2, T3> CombinedStateDelegate<UiState, State, Event>.combineCollectUpdateUiState(
        coroutineScope: CoroutineScope,
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        transform: suspend (state: State, uiState: UiState, value1: T1, value2: T2, value3: T3) -> UiState
    ): Job {
        return combine(internalStateFlow, flow1, flow2, flow3) { state, value1, value2, value3 ->
            transform(
                state,
                uiState,
                value1,
                value2,
                value3
            )
        }.onEach { newState -> updateUiState { _ -> newState } }
            .launchIn(coroutineScope)
    }

    override fun <T1, T2, T3, T4> CombinedStateDelegate<UiState, State, Event>.combineCollectUpdateUiState(
        coroutineScope: CoroutineScope,
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        flow3: Flow<T3>,
        flow4: Flow<T4>,
        transform: suspend (state: State, uiState: UiState, value1: T1, value2: T2, value3: T3, value4: T4) -> UiState
    ): Job {
        return combine(
            internalStateFlow,
            flow1,
            flow2,
            flow3,
            flow4
        ) { state, value1, value2, value3, value4 ->
            transform(
                state,
                uiState,
                value1,
                value2,
                value3,
                value4
            )
        }.onEach { newState -> updateUiState { _ -> newState } }
            .launchIn(coroutineScope)
    }
}