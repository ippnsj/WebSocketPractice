package com.example.websocketchatting

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MyWebSocketListener(private val activity: ChattingActivity) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d("WS", "소켓 handshake : $webSocket : $response")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d("WS", "text 데이터 확인 : $text")
        activity.updateChatting(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d("WS", "ByteString 데이터 확인 : $bytes")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d("WS", "소켓 onFailure : $t ${t.cause} ${webSocket.request()}")
        webSocket.cancel()
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d("WS", "소켓 onClosing")
    }

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
        const val WEB_SOCKET_URL = "ws://10.0.2.2:8080/websocket"
    }
}
