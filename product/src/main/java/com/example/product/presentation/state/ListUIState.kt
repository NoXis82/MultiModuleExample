package com.example.product.presentation.state

import androidx.compose.runtime.Immutable
import com.example.product.domain.models.Product
import com.example.product.domain.models.ProductList
import com.example.product.domain.until.DataError

@Immutable
data class ListUIState(
    val listData: ProductList?,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val errorMsg: DataError? = null,
    val productItemDetails: Product? = null
)
