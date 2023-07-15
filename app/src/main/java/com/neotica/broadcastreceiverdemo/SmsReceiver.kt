package com.neotica.broadcastreceiverdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.neotica.broadcastreceiverdemo.databinding.ActivitySmsReceiverBinding

class SmsReceiver : AppCompatActivity() {
    private lateinit var binding: ActivitySmsReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivitySmsReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.incoming_message)
        binding.btnClose.setOnClickListener { finish() }

        val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(EXTRA_SMS_MESSAGE)

        binding.apply {
            tvFrom.text = getString(R.string.from, senderNo)
            tvMessage.text = senderMessage
        }

    }

    companion object{
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }
}