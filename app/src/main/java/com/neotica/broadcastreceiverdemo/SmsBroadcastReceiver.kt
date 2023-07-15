package com.neotica.broadcastreceiverdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

class SmsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val bundle = intent.extras
        try {
            if (bundle!=null){
                val pdusObj = bundle.get("pdus") as Array<*>
                for (aPdusObj in pdusObj) {
                    val currentMessage = getIncomingMessage(aPdusObj as Any, bundle)
                    val senderNum = currentMessage.displayOriginatingAddress
                    val message = currentMessage.displayMessageBody
                    Log.d(TAG, "senderNum: $senderNum; message: $message")

                    val showSmsIntent = Intent(context, SmsReceiver::class.java)
                    showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    showSmsIntent.putExtra(SmsReceiver.EXTRA_SMS_NO, senderNum)
                    showSmsIntent.putExtra(SmsReceiver.EXTRA_SMS_MESSAGE, message)
                    context.startActivity(showSmsIntent)
                }
            }
        } catch (e: Exception){Log.d(TAG, "Exception smsReceiver $e")}
    }

    private fun getIncomingMessage(aObject: Any, bundle: Bundle):SmsMessage{
        val currentSms: SmsMessage
        val format = bundle.getString("format")
        currentSms = SmsMessage.createFromPdu(aObject as ByteArray, format)

        return currentSms
    }

    companion object{
        private val TAG = SmsBroadcastReceiver::class.java.simpleName
    }
}