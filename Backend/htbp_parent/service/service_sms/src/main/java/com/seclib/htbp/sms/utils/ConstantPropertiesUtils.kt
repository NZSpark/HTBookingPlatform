package com.seclib.htbp.sms.utils

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
import com.seclib.htbp.vo.msm.MsmVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.annotation.QueueBinding
import com.seclib.htbp.common.constant.MqConst
import org.springframework.beans.factory.annotation.Value
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
class ConstantPropertiesUtils : InitializingBean {
    @Value("\${aliyun.sms.regionId}")
    private val regionId: String? = null

    @Value("\${aliyun.sms.accessKeyId}")
    private val accessKeyId: String? = null

    @Value("\${aliyun.sms.secret}")
    private val secret: String? = null
    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        REGION_ID = regionId
        ACCESS_KEY_ID = accessKeyId
        SECRECT = secret
    }

    companion object {
        var REGION_ID: String? = null
        var ACCESS_KEY_ID: String? = null
        var SECRECT: String? = null
    }
}