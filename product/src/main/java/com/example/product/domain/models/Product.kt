package com.example.product.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Product(
    @SerialName("productId") val productId: String,
    @SerialName("productImage") val productImage: String,
    @SerialName("text") val text: String,
    @SerialName("subText") val subText: String,
    @SerialName("review") val review: String,
    @SerialName("questions") val questions: String,
    @SerialName("rating") val rating: String,
) : java.io.Serializable
