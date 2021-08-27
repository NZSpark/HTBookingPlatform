package nz.co.seclib.rabbitmq

import nz.co.seclib.rabbitmq.service.SMSService
import nz.co.seclib.rabbitmq.service.RabbitMQService
import nz.co.seclib.rabbitmq.vo.MsmVo
import android.app.PendingIntent
import android.content.Intent
import android.content.BroadcastReceiver
import android.app.Activity
import android.widget.Toast
import android.content.IntentFilter
import com.rabbitmq.client.ConnectionFactory
import nz.co.seclib.rabbitmq.R
import com.rabbitmq.client.DefaultConsumer
import kotlin.Throws
import com.rabbitmq.client.AMQP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private var rabbitMQService: RabbitMQService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rabbitMQService = RabbitMQService(this)
        rabbitMQService!!.setupConnectionFactory()
        //rabbitMQService.publishToAMQP();
        rabbitMQService!!.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        rabbitMQService?.finish()
    }
}