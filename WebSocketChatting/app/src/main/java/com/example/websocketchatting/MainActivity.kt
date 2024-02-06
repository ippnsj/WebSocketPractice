package com.example.websocketchatting

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.websocketchatting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEnterChattingButton()
    }

    private fun setupEnterChattingButton() {
        binding.btnEnterChatting.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            if (nickname.isBlank()) {
                notifyNicknameBlank()
                return@setOnClickListener
            }
            startActivity(ChattingActivity.createIntent(this, nickname))
        }
    }

    private fun notifyNicknameBlank() {
        val toast = Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT)
        toast.show()
    }
}
