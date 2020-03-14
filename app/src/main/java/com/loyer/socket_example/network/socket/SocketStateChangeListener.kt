package com.loyer.socket_example.network.socket

interface SocketStateChangeListener {
    fun onConnect()
    fun onConnectionError(error: String)
    fun onDisconnect(error: String, code: Int)
    fun onTimeOutError()
}