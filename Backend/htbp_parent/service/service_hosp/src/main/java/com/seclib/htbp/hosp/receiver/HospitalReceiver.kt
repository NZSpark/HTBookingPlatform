package com.seclib.htbp.hosp.receiver

import com.rabbitmq.client.Channel
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.hosp.service.ScheduleService
import com.seclib.htbp.common.service.RabbitService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.annotation.QueueBinding
import com.seclib.htbp.common.constant.MqConst
import kotlin.Throws
import java.io.IOException
import com.seclib.htbp.vo.order.OrderMqVo
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.stereotype.Component

@Component
class HospitalReceiver {
    @Autowired
    private val scheduleService: ScheduleService? = null

    @Autowired
    private val rabbitService: RabbitService? = null

    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(value = MqConst.QUEUE_ORDER, durable = "true"),
            exchange = Exchange(value = MqConst.EXCHANGE_DIRECT_ORDER),
            key = [MqConst.ROUTING_ORDER]
        )]
    )
    @Throws(
        IOException::class
    )
    fun receiver(orderMqVo: OrderMqVo, message: Message?, channel: Channel?) {

        //update the numbers after ordered.
        val schedule = scheduleService!!.getById(orderMqVo.scheduleId) ?: return
        schedule.reservedNumber = orderMqVo.reservedNumber
        schedule.availableNumber = orderMqVo.availableNumber
        scheduleService.update(schedule)
        //Send SMS
        val msmVo = orderMqVo.msmVo
        if (null != msmVo) {
            rabbitService!!.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo)
        }
    }
}