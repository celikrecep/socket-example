package com.loyer.socket_example.data_manager.network.socket

import com.loyer.socket_example.vo.Mock

interface SocketDataReceivedListener {
    fun onDataReceived(data: Mock)
    fun onDataReceivedFail(message: String)
}