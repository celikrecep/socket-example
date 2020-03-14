package com.loyer.socket_example.network.socket

sealed class SocketState {
    companion object {
        fun onConnected() = ConnectedState
        fun onDisconnected(message: String, code: Int) = DisconnectedState(message, code)
        fun onTimeOut() = TimeOutState
        fun onConnectionError(message: String) = ConnectionErrorState(message)
    }
}

object ConnectedState : SocketState()
data class DisconnectedState(val message: String, val code: Int) : SocketState()
object TimeOutState : SocketState()
data class ConnectionErrorState(val message: String): SocketState()
object Connecting: SocketState()


