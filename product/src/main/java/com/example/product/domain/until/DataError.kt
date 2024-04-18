package com.example.product.domain.until

sealed interface DataError: Error {
    enum class NetworkError: DataError {
        SERVER_ERROR,
        NO_INTERNET
    }
}