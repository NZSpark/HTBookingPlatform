package com.seclib.htbp.sms.receiver

import com.seclib.htbp.common.result.Result.Companion.ok
import java.text.DecimalFormat
import com.seclib.htbp.sms.utils.RandomUtil
import java.util.HashMap
import org.springframework.beans.factory.InitializingBean
import kotlin.Throws
import java.lang.Exception
import com.seclib.htbp.sms.service.SmsService
import com.aliyuncs.profile.DefaultProfile
import com.aliyuncs.IAcsClient
import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.CommonRequest
import com.aliyuncs.CommonResponse
import com.rabbitmq.client.Channel
import com.seclib.htbp.vo.msm.MsmVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.annotation.QueueBinding
import com.seclib.htbp.common.constant.MqConst
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.stereotype.Component

@Component
class SmsReceiver {
    @Autowired
    private val smsService: SmsService? = null
    @RabbitListener(
        bindings = [QueueBinding(
            value = Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
            exchange = Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = [MqConst.ROUTING_MSM_ITEM]
        )]
    )
    fun send(msmVo: MsmVo, message: Message?, channel: Channel?) {
        smsService!!.send(msmVo)
    }
}