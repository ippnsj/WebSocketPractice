package com.example.websocketchatting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.websocketchatting.databinding.ActivityChattingBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class ChattingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChattingBinding
    private lateinit var nickname: String
    private lateinit var webSocket: WebSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nickname = intent.getStringExtra(KEY_NICKNAME) ?: "알수없음"

        binding.btnSendChatting.setOnClickListener {
            val message = binding.etChatting.text.toString()
            webSocket.send("$nickname : $message")
        }
    }

    override fun onResume() {
        super.onResume()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(MyWebSocketListener.WEB_SOCKET_URL)
            .build()

        webSocket = client.newWebSocket(request, MyWebSocketListener(this))
        client.dispatcher.executorService.shutdown()
    }

    override fun onPause() {
        super.onPause()

        webSocket.close(MyWebSocketListener.NORMAL_CLOSURE_STATUS, null)
    }

    fun updateChatting(message: String) {
        val previousMessages = binding.tvChatting.text
        binding.tvChatting.text = """
            |$previousMessages
            |$message
        """.trimMargin("|")
    }

    companion object {
        private const val KEY_NICKNAME = "nickname"

        fun createIntent(context: Context, nickname: String): Intent {
            val intent = Intent(context, ChattingActivity::class.java)
            intent.putExtra(KEY_NICKNAME, nickname)
            return intent
        }
    }
}
