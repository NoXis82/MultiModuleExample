package com.example.details.viewmodel

import com.example.common_core.presentation.StateAndEventViewModel
import com.example.details.event.DetailUIEvent
import com.example.details.state.DetailUIState
import com.example.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val navigator: Navigator
) : StateAndEventViewModel<DetailUIState, DetailUIEvent>(DetailUIState(null)) {
    
    override suspend fun handleEvent(event: DetailUIEvent) {
        when (event) {
            is DetailUIEvent.Dismiss -> {
                navigator.goBack()
            }
        }
    }



}