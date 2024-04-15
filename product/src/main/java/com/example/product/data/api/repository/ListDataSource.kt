package com.example.product.data.api.repository

import com.example.product.data.api.models.ProductDto
import com.example.product.data.api.models.ResponseList

interface ListDataSource {
    suspend fun getList(): ResponseList<ProductDto>

}