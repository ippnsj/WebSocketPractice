package com.example.websocketchatting

import android.util.Log
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient

class MyStompConnection {
    private lateinit var stomp: StompClient
    private lateinit var stompConnection: Disposable
    private lateinit var topic: Disposable

    fun connect() {
        val client = OkHttpClient()

        stomp = StompClient(client, INTERVAL_MILLIS)

        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {
                    Log.d(TAG, "OPENED")
                }

                Event.Type.CLOSED -> {
                    Log.d(TAG, "CLOSED")
                }

                Event.Type.ERROR -> {
                    Log.d(TAG, "ERRER : ${it.exception}")
                }

                else -> {}
            }
        }
    }

    fun subscribe() {
        topic = stomp.join("/chat/rooms/f699eb36-8616-44ec-8380-1b1d63f9ff14")
            .subscribe { Log.i(TAG, it) }
    }

    fun unsubscribe() {
        topic.dispose()
    }

    fun send(message: String) {
        stomp.send("/chat/message", message)
    }

    fun disconnect() {
        stompConnection.dispose()
    }

    companion object {
        private const val TAG = "STOMP"
        private const val URL = "http://127.0.0.1:8080"
        private const val INTERVAL_MILLIS = 1000L
    }
}
