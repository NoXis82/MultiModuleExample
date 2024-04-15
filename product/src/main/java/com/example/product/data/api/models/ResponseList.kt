package com.example.product.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseList<E>(
    @SerialName("listResponse") val listResponse: List<E>,
    @SerialName("productLimit") val productLimit: Int,
    @SerialName("totalCount") val totalCount: Int,
)
