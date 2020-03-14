package com.loyer.socket_example.data_manager.network.socket

import com.google.gson.Gson
import com.loyer.socket_example.data_manager.network.di.JSON_CONVERTER
import okhttp3.*
import okio.ByteString
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

private const val SOCKET_URL = "wss://echo.websocket.org"
private const val CLOSED_FROM_APP = 1000
private const val SOCKET_CLOSE_REASON = "hey wazzup!"
private const val UNKNOWN_ERROR = "unknown_error"
private const val PARSE_EXCEPTION = "json parse exception"
class SocketManagerImpl @Inject constructor(
    @Named(JSON_CONVERTER) private val jsonConverter: Gson
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
        //val data = Util.convertFromJson<Mock>(text, jsonConverter)
        //_socketDataChangeListener?.onDataReceived(data)
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