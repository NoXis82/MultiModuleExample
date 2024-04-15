package com.example.product.domain.models

data class ProductList(
    val productList: List<Product>?,
    val productLimit: Int?,
    val totalCount: Int?,
)
