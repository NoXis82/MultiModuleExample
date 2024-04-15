package com.example.product.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("productId") val productId: String,
    @SerialName("productImage") val productImage: String,
    @SerialName("text") val text: String,
    @SerialName("subText") val subText: String,
    @SerialName("review") val review: String,
    @SerialName("questions") val questions: String,
    @SerialName("rating") val rating: String,
)
