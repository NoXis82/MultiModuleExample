package com.example.product.domain.use_case

import com.example.product.domain.models.ProductList
import kotlinx.coroutines.flow.Flow

interface GetListUseCase {
    fun getProductList(): Flow<ProductList>

}