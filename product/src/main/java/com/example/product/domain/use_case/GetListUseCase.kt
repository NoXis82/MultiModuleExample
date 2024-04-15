package com.example.product.domain.use_case

import com.example.product.domain.models.ProductList
import com.example.product.presentation.viewmodel.DataState
import kotlinx.coroutines.flow.Flow

interface GetListUseCase {
    fun getProductList(): Flow<ProductList>

}