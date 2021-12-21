package com.behzoddev.hilttutorial.util

sealed class NetworkResult<out T: Any> {
    data class OnSuccess<out T: Any>(val data: T) : NetworkResult<T>()
    data class OnFailure(val throwable: Throwable) : NetworkResult<Nothing>()
}