package com.example.product.presentation.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_core.components.ErrorComponent
import com.example.product.domain.models.Product
import com.example.product.presentation.event.ListUIEvent
import com.example.product.presentation.viewmodel.ListViewModel

@Composable
fun ProductListScreen() {
    val viewModel: ListViewModel = hiltViewModel()
    val stateUi by viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        viewModel.onEvent(ListUIEvent.GetList)
    }

    when {
        stateUi.isLoading -> LoadingComponent()

        stateUi.error != null -> ErrorComponent(error = stateUi.error)

        stateUi.listData != null -> {
            VerticalSection(stateUi.listData?.productList ?: emptyList()) {
                viewModel.onEvent(ListUIEvent.ProductClicked(it))
            }
        }
        stateUi.productItemDetails != null -> {
            Test(productItem = stateUi.productItemDetails)
        }
    }

    BackHandler(enabled = true) {
        viewModel.onEvent(ListUIEvent.Dismiss)
    }
}


@Composable
fun Test(productItem: Product?) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "$productItem")
    }
}

@Composable
fun VerticalSection(productList: List<Product>, onEvent: (productItem: Product) -> Unit) {
    // val products = remember { section.productItem }
    LazyColumn() {
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
