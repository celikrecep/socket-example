package com.loyer.socket_example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.loyer.socket_example.network.util.Resource
import com.loyer.socket_example.vo.Mock
import javax.inject.Inject

class MainViewModel @Inject constructor(
    repository: MainRepository
) : ViewModel() {
    private val _onFetchMockList = MutableLiveData(false)

    val mockList: LiveData<Resource<List<Mock>>> = Transformations.switchMap(_onFetchMockList) {
        if (it)
            repository.onFetchMockList()
        else
            InitialLiveData.create(Resource.success(emptyList()))
    }

    fun onFetchMockList(isFetchMockList: Boolean) {
        if (_onFetchMockList.value == isFetchMockList)
            return
        _onFetchMockList.value = isFetchMockList
    }
}