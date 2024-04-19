package com.example.product.domain.util

sealed interface DataError: Error {
    enum class NetworkError: DataError {
        SERVER_ERROR,
        NO_INTERNET
    }
}