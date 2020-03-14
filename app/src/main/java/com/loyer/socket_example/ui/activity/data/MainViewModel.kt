package com.loyer.socket_example.ui.activity.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.loyer.socket_example.data_manager.network.repository.MainRepository
import com.loyer.socket_example.data_manager.network.socket.*
import com.loyer.socket_example.data_manager.network.util.Resource
import com.loyer.socket_example.util.InitialLiveData
import com.loyer.socket_example.vo.Mock
import javax.inject.Inject

class MainViewModel @Inject constructor(
    repository: MainRepository,
    private val socketManager: SocketManager
) : ViewModel(), SocketStateChangeListener, SocketDataReceivedListener {
    private val _onFetchMockList = MutableLiveData(false)
    private val _socketState = MutableLiveData<SocketState>(Connecting)
    private val _socketResponse = MutableLiveData<Resource<Mock>>()
    private val _loginState = MutableLiveData(false)

    init {
        socketManager.setDataReceivedListener(this)
        socketManager.setStateChangeListener(this)
    }

    val mockList: LiveData<Resource<List<Mock>>> = Transformations.switchMap(_onFetchMockList) {
        if (it)
            repository.onFetchMockList()
        else
            InitialLiveData.create(Resource.success(emptyList()))
    }

    val socketState: LiveData<SocketState>
        get() = _socketState

    val socketResponse: LiveData<Resource<Mock>>
        get() = _socketResponse

    val loginState: LiveData<Boolean>
        get() = _loginState

    override fun onConnect() {
        _socketState.postValue(SocketState.onConnected())
    }

    override fun onConnectionError(error: String) {
        _socketState.postValue(SocketState.onConnectionError(error))
    }

    override fun onDisconnect(error: String, code: Int) {
        _socketState.postValue(SocketState.onDisconnected(error, code))
    }

    override fun onTimeOutError() {
        _socketState.postValue(SocketState.onTimeOut())
    }

    override fun onDataReceived(data: Mock) {
        _socketResponse.postValue(Resource.success(data))
    }

    override fun onDataReceivedFail(message: String) {
        _socketResponse.postValue(Resource.error(message, null))
    }

    override fun onChangeLoginState(isLogin: Boolean) {
        _loginState.postValue(isLogin)
    }

    fun onFetchMockList(isFetchMockList: Boolean) {
        if (_onFetchMockList.value == isFetchMockList)
            return
        _onFetchMockList.value = isFetchMockList
    }

    fun onConnectSocket() {
        socketManager.onConnect()
    }

    fun onDisconnectSocket() {
        socketManager.onDisconnect()
    }

    fun sendData(text: String) {
        socketManager.sendData(text)
    }
}