package com.example.product.data.api

import com.example.product.data.api.models.ProductDto
import com.example.product.data.api.models.ResponseList
import retrofit2.Response
import retrofit2.http.GET

interface ListApi {
    @GET("/basaransuleyman/suleyman-basaranoglu-json/main/list-page-paging-first")
    suspend fun getList() : Response<ResponseList<ProductDto>>
}