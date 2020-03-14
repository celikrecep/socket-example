package com.loyer.socket_example.network.socket

interface SocketManager {
    fun onConnect()
    fun onDisconnect()
    fun setDataReceivedListener(listener: SocketDataReceivedListener)
    fun setStateChangeListener(listener: SocketStateChangeListener)
    fun sendData(text: String)
}