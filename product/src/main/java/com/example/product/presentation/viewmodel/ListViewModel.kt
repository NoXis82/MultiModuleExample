package com.example.product.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common_core.presentation.StateAndEventViewModel
import com.example.product.domain.use_case.GetListUseCase
import com.example.product.presentation.event.ListUIEvent
import com.example.product.presentation.state.ListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getListUseCase: Provider<GetListUseCase>,
) : StateAndEventViewModel<ListUIState, ListUIEvent>(ListUIState(null)) {
    override suspend fun handleEvent(event: ListUIEvent) {
        when (event) {
            ListUIEvent.Dismiss -> {
                Log.d(TAG, "handleEvent: Dismiss, to navigation")
            }

            ListUIEvent.GetList -> {
                getProductList()
            }
        }
    }

    private fun getProductList() {
        viewModelScope.launch {
            getListUseCase.get().getProductList()
                .onStart {
                    updateUiState { copy(isLoading = true) }
                }
                .catch {
                    updateUiState { copy(error = it) }
                }
                .collect{
                    updateUiState { copy(isLoading = false, listData = it) }
                }
        }
    }

    companion object {
        private const val TAG = "ListViewModel"
    }

}
