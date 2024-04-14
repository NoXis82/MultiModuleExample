package com.example.navigationtestapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


/**
 * Реализация делегата для управления состоянием.
 * Этот делегат хранит и управляет состоянием пользовательского интерфейса.
 *
 * @param mutexState Мьютекс для синхронизации доступа к состоянию.
 * @param initialUiState Начальное состояние пользовательского интерфейса.
 * @param singleLiveEventCapacity Емкость канала для SingleLiveEvent.
 */
class UIStateDelegateImpl<UiState, Event>(
    initialUiState: UiState,
    singleLiveEventCapacity: Int = Channel.BUFFERED,
    private val mutexState: Mutex = Mutex()
) : UiStateDelegate<UiState, Event> {

    /**
     * Источник истины, который управляет нашим приложением.
     */
    private val uiMutableStateFlow = MutableStateFlow(initialUiState)
    private val singleEventsChannel = Channel<Event>(singleLiveEventCapacity)

    override val uiStateFlow: StateFlow<UiState>
        get() = uiMutableStateFlow.asStateFlow()

    override val singleEvents: Flow<Event>
        get() = singleEventsChannel.receiveAsFlow()

    override val UiStateDelegate<UiState, Event>.uiState: UiState
        get() = uiMutableStateFlow.value

    override suspend fun UiStateDelegate<UiState, Event>.sendEvent(event: Event) {
        singleEventsChannel.send(event)
    }

    override fun UiStateDelegate<UiState, Event>.asyncUpdateUiState(
        coroutineScope: CoroutineScope,
        transform: (state: UiState) -> UiState
    ): Job {
        return coroutineScope.launch {
            updateUiState { state ->
                transform(state)
            }
        }
    }

    override suspend fun UiStateDelegate<UiState, Event>.updateUiState(transform: (uiState: UiState) -> UiState) {
        mutexState.withLock {
            uiMutableStateFlow.emit(transform(uiState))
        }
    }


}