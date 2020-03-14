package com.loyer.socket_example.data_manager.network.socket

import com.loyer.socket_example.base.AppExecutors
import com.loyer.socket_example.vo.Mock
import okhttp3.*
import okio.ByteString
import timber.log.Timber
import java.lang.NumberFormatException
import javax.inject.Inject

private const val SOCKET_URL = "wss://echo.websocket.org"
private const val CLOSED_FROM_APP = 1000
private const val SOCKET_CLOSE_REASON = "hey wazzup!"
private const val UNKNOWN_ERROR = "unknown_error"
private const val LOGOUT = "LOGOUT"
private const val LOGIN = "LOGIN"
private const val UNSUPPORTED_INPUT = "unsupported text"

class SocketManagerImpl @Inject constructor(
    private val appExecutors: AppExecutors
) : WebSocketListener(), SocketManager {
    private var _socketStateChangeListener: SocketStateChangeListener? = null
    private var _socketDataChangeListener: SocketDataReceivedListener? = null
    private val client by lazy {
        OkHttpClient()
    }
    private val request by lazy {
        Request.Builder().url(SOCKET_URL).build()
    }
    private var webSocket: WebSocket? = null

    override fun onOpen(webSocket: WebSocket, response: Response) {
        _socketStateChangeListener?.onConnect()
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Timber.d("webSocket onMessage $bytes")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        when {
            text.contains(LOGIN) && !text.any { it.isDigit() } -> _socketDataChangeListener?.onChangeLoginState(true)
            text.contains(LOGOUT) && !text.any { it.isDigit() } -> _socketDataChangeListener?.onChangeLoginState(false)
            else -> {
                var id = -1
                try {
                    id = String(text.takeWhile {
                        it.isDigit()
                    }.toCharArray()).toInt()
                } catch (e: NumberFormatException) {
                    Timber.d(e.localizedMessage)
                }
                if (id == -1) {
                    _socketDataChangeListener?.onDataReceivedFail(UNSUPPORTED_INPUT)
                    return
                }
                val name = text.replace("\\d".toRegex(), "")
                appExecutors.mainThread().execute {
                    _socketDataChangeListener?.onDataReceived(Mock(id, name))
                }
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Timber.d("webSocket onClosing $code reason: $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        _socketStateChangeListener?.onDisconnect(reason, code)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        _socketStateChangeListener?.onConnectionError(t.localizedMessage ?: UNKNOWN_ERROR)
    }

    override fun onConnect() {
        if (webSocket == null)
            webSocket = client.newWebSocket(request, this)
    }

    override fun onDisconnect() {
        webSocket?.close(CLOSED_FROM_APP, SOCKET_CLOSE_REASON)
    }

    override fun setDataReceivedListener(listener: SocketDataReceivedListener) {
        _socketDataChangeListener = listener
    }

    override fun setStateChangeListener(listener: SocketStateChangeListener) {
        _socketStateChangeListener = listener
    }

    override fun sendData(text: String) {
        webSocket?.send(text)
    }
}