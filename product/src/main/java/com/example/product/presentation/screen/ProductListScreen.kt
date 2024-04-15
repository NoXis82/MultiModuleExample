package com.example.product.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.product.domain.models.Product
import com.example.product.domain.models.ProductList
import com.example.product.presentation.viewmodel.DataState
import com.example.product.presentation.viewmodel.ListViewModel
import com.example.product.presentation.viewmodel.MainStateEvent

@Composable
fun ProductListScreen() {
    val viewModel: ListViewModel = hiltViewModel()
    val state by viewModel.dataState.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        when (state) {
            DataState.Loading -> {
                LoadingComponent()
                viewModel.setStateEvent(MainStateEvent.GetProductEvents)
            }

            is DataState.Error -> {
                viewModel.setStateEvent(MainStateEvent.None)
            }

            is DataState.Success -> {
                (state as DataState.Success<ProductList>).data.productList?.let {
                    VerticalSection(it) {
                        Log.d(this.javaClass.name, ">>> Product: $it")
                    }
                }
            }
        }
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
