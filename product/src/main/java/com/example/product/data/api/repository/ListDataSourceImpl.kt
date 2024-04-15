package com.example.product.data.api.repository

import com.example.network.utils.handleCall
import com.example.product.data.api.ListApi
import com.example.product.data.api.models.ProductDto
import com.example.product.data.api.models.ResponseList
import javax.inject.Inject

class ListDataSourceImpl @Inject constructor(
    private val api: ListApi
) : ListDataSource {
    override suspend fun getList(): ResponseList<ProductDto> {
        return handleCall {
            api.getList()
        }
    }
}