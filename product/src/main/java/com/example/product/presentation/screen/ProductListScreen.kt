package com.example.product.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_core.components.ErrorComponent
import com.example.common_core.components.ErrorComponentTest
import com.example.product.domain.models.Product
import com.example.product.domain.util.DataError
import com.example.product.presentation.event.ListUIEvent
import com.example.product.presentation.viewmodel.ListViewModel

@Composable
fun ProductListScreen(paddingValues: PaddingValues) {
    val viewModel: ListViewModel = hiltViewModel()
    val stateUi by viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        viewModel.onEvent(ListUIEvent.GetList)
    }

    when {
        stateUi.isLoading -> LoadingComponent()

        stateUi.error != null -> ErrorComponent(error = stateUi.error)

        stateUi.errorMsg != null -> {
            when (val error = stateUi.errorMsg) {
                is DataError.NetworkError -> {
                    ErrorComponentTest(error = error.name)
                }
                else -> {}
            }
        }

        stateUi.listData != null -> {
            VerticalSection(stateUi.listData?.productList ?: emptyList(), paddingValues) {
                viewModel.onEvent(ListUIEvent.ProductClicked(it))
            }
        }
    }

    BackHandler(enabled = true) {
//        viewModel.onEvent(ListUIEvent.Dismiss)
    }
}


@Composable
fun VerticalSection(
    productList: List<Product>,
    paddingValues: PaddingValues,
    onEvent: (productItem: Product) -> Unit
) {
    // val products = remember { section.productItem }
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(items = productList, key = { it.productId }) { productItem ->
            VerticalItemCard(
                item = productItem,
                onProductClick = { onEvent(productItem) }
            )
        }
    }
}

@Composable
fun LoadingComponent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}
