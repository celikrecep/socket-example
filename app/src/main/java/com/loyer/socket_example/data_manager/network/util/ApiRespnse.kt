package com.loyer.socket_example.data_manager.network.util

import retrofit2.Response


sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(
                error.message ?: "unknown error"
            )
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(
                    errorMsg ?: "unknown error"
                )
            }
        }
    }
}

data class ApiErrorResponse<T>(val errorMessage: String): ApiResponse<T>()
data class ApiSuccessResponse<T>(val body: T): ApiResponse<T>()
class ApiEmptyResponse<T>: ApiResponse<T>()