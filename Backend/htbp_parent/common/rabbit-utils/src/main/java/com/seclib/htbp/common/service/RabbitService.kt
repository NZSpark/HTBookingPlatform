package com.seclib.htbp.common.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
open class RabbitService {
    @Autowired
    private val rabbitTemplate: RabbitTemplate? = null

    /**
     * 发送消息
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息
     */
    fun sendMessage(exchange: String?, routingKey: String?, message: Any?): Boolean {
        rabbitTemplate!!.convertAndSend(exchange, routingKey, message)
        return true
    }
}