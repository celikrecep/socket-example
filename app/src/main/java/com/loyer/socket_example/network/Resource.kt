package com.loyer.socket_example.network

import com.loyer.socket_example.network.Status.*
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Success, data, null)
        }

        fun <T> error(message: String?, data: T?): Resource<T> {
            return Resource(Error, data, message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Loading, data, null)
        }
    }
}