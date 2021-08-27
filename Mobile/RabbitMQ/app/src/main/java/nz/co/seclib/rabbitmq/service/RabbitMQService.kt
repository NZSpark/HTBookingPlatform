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
import nz.co.seclib.rabbitmq.R
import kotlin.Throws
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rabbitmq.client.*
import java.io.IOException
import java.lang.Exception

class RabbitMQService(private val context: Context) {
    var smsService = SMSService(context)
    var factory: ConnectionFactory = ConnectionFactory()
    var subscribeThread: Thread? = null
    var publishThread: Thread? = null
    var exchangeName: String = ""
    var routingKey: String = ""
    var queueName: String = ""

    fun setupConnectionFactory() {

        factory.setUsername(context.getString(R.string.user_name)) //"guest");
        factory.setPassword(context.getString(R.string.password)) //"guest");
        factory.setVirtualHost(context.getString(R.string.virtual_host)) //"/");
        factory.setHost(context.getString(R.string.host)) //"192.168.1.10");
        factory.setPort(context.getString(R.string.port).toInt()) //5672);

        exchangeName = context.getString(R.string.exchange_name) // "exchange.direct.msm";
        routingKey = context.getString(R.string.routing_key) //"msm.item";
        queueName = context.getString(R.string.queue_name) //"queue.msm.item";
    }

    fun publishToAMQP() {
        //sleep and then try again
        publishThread = Thread {
            try {
                val connection = factory.newConnection()
                val ch = connection.createChannel()
                ch.exchangeDeclare(exchangeName, "direct", true)
                ch.queueDeclare(queueName, true, false, false, null)
                ch.queueBind(queueName, exchangeName, routingKey)
                val messageBodyBytes = "Hello, world!".toByteArray()
                ch.basicPublish(exchangeName, routingKey, null, messageBodyBytes)
            } catch (e: Exception) {
                Log.d("", "Connection broken: " + e.javaClass.name)
            }
        }
        publishThread!!.start()
    }

    fun subscribe() {
        //sleep and then try again
        subscribeThread = Thread {
            try {
                val connection = factory.newConnection()
                val ch = connection.createChannel()
                ch.exchangeDeclare(exchangeName, "direct", true)
                ch.queueDeclare(queueName, true, false, false, null)
                ch.queueBind(queueName, exchangeName, routingKey)
                //val consumer: Consumer = DefaultConsumer(ch)
                val autoAck = false
                ch.basicConsume(queueName, autoAck, "HtbpConsumerTag",
                        object : DefaultConsumer(ch) {
                            @Throws(IOException::class)
                            override fun handleDelivery(consumerTag: String,
                                                        envelope: Envelope,
                                                        properties: AMQP.BasicProperties,
                                                        body: ByteArray) {
                                val routingKey = envelope.getRoutingKey()
                                val contentType = properties.getContentType()
                                val deliveryTag = envelope.getDeliveryTag()
                                val message = String(body)
                                smsService.SendMessage(message)
                                Log.d("", "[r] $message")
                                println(message)
                                ch.basicAck(deliveryTag, false)
                            }
                        })
                while (true) {
                    Thread.sleep(1000)
                }
            } catch (e1: Exception) {
                Log.d("", "Connection broken: " + e1.javaClass.name)
            }
        }
        subscribeThread!!.start()
    }

    fun finish() {
        if (publishThread != null) publishThread!!.interrupt()
        if (subscribeThread != null) subscribeThread!!.interrupt()
    }

    init {
        smsService = SMSService(context)
    }
}