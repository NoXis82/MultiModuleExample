package com.example.product.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.product.domain.models.ProductList
import com.example.product.domain.use_case.GetListUseCase
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getListUseCase: Provider<GetListUseCase>,
): ViewModel() {


    private val _dataState = MutableStateFlow<DataState<ProductList>>(DataState.Loading)
    val dataState: StateFlow<DataState<ProductList>>
        get() = _dataState
    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetProductEvents -> {
                    getListUseCase.get().getProductList()
                        .onEach {
                            _dataState.value = DataState.Success(it)
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {
                    Log.d(TAG, "setStateEvent: None")
                }
            }
        }
    }

    companion object {
        private const val TAG = "ListViewModel"
    }


}
sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
}
sealed class MainStateEvent {
    data object GetProductEvents : MainStateEvent()
    data object None : MainStateEvent()
}
@Immutable
data class ListUIState(
    val listData: ProductList?,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)