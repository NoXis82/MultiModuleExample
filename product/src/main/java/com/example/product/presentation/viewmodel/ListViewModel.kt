package com.example.product.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.common_core.presentation.StateAndEventViewModel
import com.example.common_core.util.Until.toJson
import com.example.navigation.Navigator
import com.example.product.domain.models.Product
import com.example.product.domain.until.Result
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
    private val navigator: Navigator
) : StateAndEventViewModel<ListUIState, ListUIEvent>(ListUIState(null)) {
    override suspend fun handleEvent(event: ListUIEvent) {
        when (event) {
            is ListUIEvent.Dismiss -> {
                navigator.goBack()
            }

            is ListUIEvent.GetList -> {
                getProductList()
            }

            is ListUIEvent.ProductClicked -> {
//                onProductClicked(event.item)
                val product = Product(
                    productId = event.item.productId,
                    productImage = "",
                    text = event.item.text,
                    subText = event.item.subText,
                    review = event.item.review,
                    questions = event.item.questions,
                    rating = event.item.rating,
                )
                val str = product.toJson()
                Log.d(TAG, "handleEvent:ProductClicked $str")
                navigator.navigateTo("detail/$str")
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
                    updateUiState { copy(error = it, isLoading = false) }
                }
                .collect { result ->
                    when(result) {
                        is Result.Success -> {
                            updateUiState { copy(isLoading = false, listData = result.data) }
                        }

                        is Result.Error -> {
                            updateUiState { copy(errorMsg = result.error, isLoading = false) }
                        }
                    }

                }
        }
    }

    companion object {
        private const val TAG = "ListViewModel"
    }

}
