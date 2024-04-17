package com.example.details.state

import androidx.compose.runtime.Immutable
import com.example.details.domain.model.ProductItem

@Immutable
data class DetailUIState(
    val details: ProductItem?,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)
