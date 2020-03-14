package com.loyer.socket_example.network

import androidx.lifecycle.LiveData
import com.loyer.socket_example.network.util.ApiResponse
import com.loyer.socket_example.vo.MockResponse
import retrofit2.http.GET

interface Api {

    @GET("emredirican/mock-api/db")
    fun fetchMockList(): LiveData<ApiResponse<MockResponse>>
}