package com.loyer.socket_example.data_manager.network.socket

interface SocketStateChangeListener {
    fun onConnect()
    fun onConnectionError(error: String)
    fun onDisconnect(error: String, code: Int)
    fun onTimeOutError()
}