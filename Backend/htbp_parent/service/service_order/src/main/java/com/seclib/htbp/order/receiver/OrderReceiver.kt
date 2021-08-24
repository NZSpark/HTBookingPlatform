package com.seclib.htbp.order.receiver

import com.rabbitmq.client.Channel
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.order.service.OrderService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.annotation.QueueBinding
import com.seclib.htbp.common.constant.MqConst
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.stereotype.Component
import kotlin.Throws
import java.io.IOException

@Component
class OrderReceiver {
    @Autowired
    private val orderService: OrderService? = null

    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(value = MqConst.QUEUE_TASK_8, durable = "true"),
            exchange = Exchange(value = MqConst.EXCHANGE_DIRECT_TASK),
            key = [MqConst.ROUTING_TASK_8]
        )]
    )
    @Throws(
        IOException::class
    )
    fun patientTips(message: Message?, channel: Channel?) {
        orderService!!.patientTips()
    }
}