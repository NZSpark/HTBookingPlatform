package nz.co.seclib.rabbitmq.service

import nz.co.seclib.rabbitmq.service.SMSService
import nz.co.seclib.rabbitmq.service.RabbitMQService
import nz.co.seclib.rabbitmq.vo.MsmVo
import android.app.PendingIntent
import android.content.Intent
import android.content.BroadcastReceiver
import android.app.Activity
import android.content.Context
import android.widget.Toast
import android.content.IntentFilter
import com.rabbitmq.client.ConnectionFactory
import nz.co.seclib.rabbitmq.R
import com.rabbitmq.client.DefaultConsumer
import kotlin.Throws
import com.rabbitmq.client.AMQP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import com.alibaba.fastjson.JSONObject
import java.lang.Exception
import java.util.*

class SMSService(private val context: Context) {
    fun SendMessage(sIn: String?): Boolean {
        if (sIn == "") return false
        val msmVo = JSONObject.parseObject(sIn, MsmVo::class.java)
                ?: return false
        if (msmVo.phone == "") return false
        var sMessage: String? = ""
        if (msmVo.templateCode != null) {
            sMessage = "Authentication code: " + msmVo.templateCode + " , The code will be expired in 60s."
            mySendMessage(msmVo.phone, sMessage)
            return true
        }
        sMessage = JSONObject.toJSONString(msmVo.param)
        sMessage = sMessage + Date().toString()
        mySendMessage(msmVo.phone, sMessage)
        return true
    }

    fun mySendMessage(sPhoneNumber: String?, sMessage: String?): Boolean {
        try {
            val sHint1 = "SMS_SENT"
            val sHint2 = "SMS_DELIVERED"
            val sentPI = PendingIntent.getBroadcast(context, 0,
                    Intent(sHint1), 0)

            //---when the SMS has been sent---
            context.registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(arg0: Context?, arg1: Intent?) {
                    if (resultCode == Activity.RESULT_OK) {
                        Toast.makeText(context, "SMS sent.",
                                Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "SMS could not be sent",
                                Toast.LENGTH_SHORT).show()
                    }
                }
            }, IntentFilter(sHint1))
            val deliveredPI = PendingIntent.getBroadcast(context, 0,
                    Intent(sHint2), 0)

            //---when the SMS has been sent---
            context.registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(arg0: Context?, arg1: Intent?) {
                    if (resultCode == Activity.RESULT_OK) {
                        Toast.makeText(context, "SMS delivered.",
                                Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "SMS could not be delivered.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
            }, IntentFilter(sHint2))
            val sms = SmsManager.getDefault()

            //sMessage sometimes is too long and phone will refuse to send it.
            //sms.sendTextMessage(sPhoneNumber, null, sMessage, sentPI, deliveredPI);
            val parts = sms.divideMessage(sMessage)
            val sendList = ArrayList<PendingIntent?>()
            sendList.add(sentPI)
            val deliverList = ArrayList<PendingIntent?>()
            deliverList.add(deliveredPI)
            sms.sendMultipartTextMessage(sPhoneNumber, null, parts, sendList, deliverList)
        } catch (e: Exception) {
            System.console().printf("%s", e.toString())
            return false
        }
        return true
    }
}