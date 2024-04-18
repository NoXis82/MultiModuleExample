package com.example.network.utils

import retrofit2.Response
import java.io.IOException


fun <T> Response<T>.handleResponse(): T {
    return try {
        this.takeIf { it.isSuccessful }?.body()!!
    } catch (e: Exception) {
        throw GenericException(
            message = e.message,
            hasUserFriendlyMessage = false
        )
    }
}

suspend fun <T> handleCall(block: suspend () -> Response<T>): T {
    return try {
        block.invoke().handleResponse()
    } catch (e: Exception) {
        throw GenericException(
            message = e.message,
            hasUserFriendlyMessage = false
        )
    }
}

data class GenericException(
    override val message: String?,
    val hasUserFriendlyMessage: Boolean
) : IOException()