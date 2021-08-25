package com.seclib.htbp.sms.controller

import com.seclib.htbp.common.result.Result.Companion.ok
import com.seclib.htbp.sms.service.SmsService
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.common.result.Result
import com.seclib.htbp.common.result.Result.Companion.fail
import com.seclib.htbp.sms.utils.RandomUtil
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.util.StringUtils
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/api/sms")
class SmsApiController {
    @Autowired
    private val smsService: SmsService? = null

    @Autowired
    private val redisTemplate: RedisTemplate<String, String?>? = null
    @GetMapping("send/{phone}")
    fun sendCode(@PathVariable phone: String): Result<*> {
        var code = redisTemplate!!.opsForValue()[phone]
        if (!StringUtils.isEmpty(code)) {
//            return Result.ok();
            return ok(code)
        }
        code = RandomUtil.getSixBitRandom()
        val isSend = smsService!!.send(phone, code)
        return if (isSend) {
            redisTemplate.opsForValue()[phone, code, 2] = TimeUnit.MINUTES
            //            return Result.ok();
            ok(code) //return code for test
        } else {
            fail("SMS send error.")
        }
    }
}