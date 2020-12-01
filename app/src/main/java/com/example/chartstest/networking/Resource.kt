package com.example.chartstest.networking

/**
 * Simple wrapper for networking responses
 */
data class Resource<out T>(val status: ResponseStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = ResponseStatus.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = ResponseStatus.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> = Resource(status = ResponseStatus.LOADING, data = data, message = null)
    }
}