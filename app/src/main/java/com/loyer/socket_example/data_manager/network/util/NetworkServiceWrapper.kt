package com.loyer.socket_example.data_manager.network.util

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.loyer.socket_example.util.AbsentLiveData
import com.loyer.socket_example.base.AppExecutors

abstract class NetworkServiceWrapper<ResultType, RequestType>
@MainThread constructor(
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        fetchFromNetwork()

    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.mainThread().execute {
                        setValue(Resource.success(onParseResponse(response.body)))
                    }
                }
                is ApiEmptyResponse -> {
                    onFetchEmpty()
                    appExecutors.mainThread().execute {
                        result.addSource(fail()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is ApiErrorResponse -> {
                    appExecutors.mainThread().execute {
                        result.addSource(fail()) { error ->
                            setValue(Resource.error(response.errorMessage, error))
                        }
                    }
                    onFetchFailed()
                }
            }
        }
    }

    private fun fail(): LiveData<ResultType> {
        return AbsentLiveData.create()
    }

    protected open fun onFetchFailed() {}

    protected open fun onFetchEmpty() {
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>


    @MainThread
    protected abstract fun onParseResponse(data: RequestType): ResultType

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}