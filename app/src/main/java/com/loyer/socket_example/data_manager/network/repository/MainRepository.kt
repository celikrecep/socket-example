package com.loyer.socket_example.data_manager.network.repository

import androidx.lifecycle.LiveData
import com.loyer.socket_example.base.AppExecutors
import com.loyer.socket_example.data_manager.network.Api
import com.loyer.socket_example.data_manager.network.util.ApiResponse
import com.loyer.socket_example.data_manager.network.util.NetworkServiceWrapper
import com.loyer.socket_example.data_manager.network.util.Resource
import com.loyer.socket_example.vo.Mock
import com.loyer.socket_example.vo.MockResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val api: Api) {

    fun onFetchMockList(): LiveData<Resource<List<Mock>>> {
        return object : NetworkServiceWrapper<List<Mock>, MockResponse>(appExecutors) {
            override fun onParseResponse(data: MockResponse): List<Mock> {
                return data.data
            }

            override fun createCall(): LiveData<ApiResponse<MockResponse>> =
                api.fetchMockList()
        }.asLiveData()
    }

}