package com.example.product.data.domain_impl.mapper

import com.example.product.data.api.models.ProductDto
import com.example.product.data.api.models.ResponseList
import com.example.product.domain.models.Product
import com.example.product.domain.models.ProductList

fun ResponseList<ProductDto>.mapToListData(): ProductList {
    val domainList = this.listResponse.map { productDto ->
        productDto.toDomainProduct()
    }
    return ProductList(
        productList = domainList,
        productLimit = this.productLimit,
        totalCount = this.totalCount
    )
}

private fun ProductDto.toDomainProduct(): Product {
    return Product(
        productId = this.productId,
        productImage = this.productImage,
        text = this.text,
        subText = this.subText,
        review = this.review,
        questions = this.questions,
        rating = this.rating
    )
}