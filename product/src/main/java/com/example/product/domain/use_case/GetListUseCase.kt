package com.example.product.domain.use_case

import com.example.product.domain.models.ProductList
import com.example.product.domain.until.DataError
import com.example.product.domain.until.Result
import kotlinx.coroutines.flow.Flow

interface GetListUseCase {
    fun getProductList(): Flow<Result<ProductList, DataError.NetworkError>>

}