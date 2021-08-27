package com.seclib.htbp.sms.service.impl

import com.alibaba.fastjson.JSONObject
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
import com.aliyuncs.exceptions.ClientException
import com.aliyuncs.exceptions.ServerException
import com.aliyuncs.http.MethodType
import com.seclib.htbp.vo.msm.MsmVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.annotation.QueueBinding
import com.seclib.htbp.common.constant.MqConst
import com.seclib.htbp.common.service.RabbitService
import com.seclib.htbp.sms.utils.ConstantPropertiesUtils
import org.joda.time.DateTime
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
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
open class SmsServiceImpl : SmsService {

    @Autowired
    val rabbitService:RabbitService? = null

    override fun send(phone: String?, code: String?): Boolean {
        if (StringUtils.isEmpty(phone)) {
            return false
        }

        //--------mobile phone--------<<
        val msmVo = MsmVo()
        msmVo.phone = phone
        msmVo.templateCode = code
        val param =  mutableMapOf<String, Any?>(
            Pair<String,Any?>("title", "title"),
            Pair<String,Any?>("name", "name")
        )
        msmVo.param = param
        return rabbitService!!.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo)
        //----------------------------->>

        /*
        val profile = DefaultProfile.getProfile(
            ConstantPropertiesUtils.Companion.REGION_ID,
            ConstantPropertiesUtils.Companion.ACCESS_KEY_ID,
            ConstantPropertiesUtils.Companion.SECRECT
        )
        val client: IAcsClient = DefaultAcsClient(profile)
        val request = CommonRequest()
        //request.setProtocol(ProtocolType.HTTPS);
        request.method = MethodType.POST
        request.domain = "dysmsapi.aliyuncs.com"
        request.version = "2017-05-25"
        request.action = "SendSms"

        //手机号
        request.putQueryParameter("PhoneNumbers", phone)
        //签名名称
        request.putQueryParameter("SignName", "我的谷粒在线教育网站")
        //模板code
        request.putQueryParameter("TemplateCode", "SMS_180051135")
        //验证码  使用json格式   {"code":"123456"}
        val param = mutableMapOf<String,String?>()
        param["code"] = code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param))
        try {
            val response = client.getCommonResponse(request)
            println(response.data)
            return response.httpResponse.isSuccess
        } catch (e: ServerException) {
            e.printStackTrace()
        } catch (e: ClientException) {
            e.printStackTrace()
        }

//        return false;
        return true //for test.

         */
    }

    override fun send(msmVo: MsmVo): Boolean {
        //--------mobile phone--------<<
        return if (!StringUtils.isEmpty(msmVo.phone)) {
            rabbitService!!.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo)
        } else false
        //----------------------------->>

        return if (!StringUtils.isEmpty(msmVo.phone)) {
            this.send(msmVo.phone, msmVo.param)
        } else false
    }

    private fun send(phone: String?, param: Map<String, Any?>?): Boolean {
        if (StringUtils.isEmpty(phone)) {
            return false
        }
        val profile = DefaultProfile.getProfile(
            ConstantPropertiesUtils.Companion.REGION_ID,
            ConstantPropertiesUtils.Companion.ACCESS_KEY_ID,
            ConstantPropertiesUtils.Companion.SECRECT
        )
        val client: IAcsClient = DefaultAcsClient(profile)
        val request = CommonRequest()
        //request.setProtocol(ProtocolType.HTTPS);
        request.method = MethodType.POST
        request.domain = "dysmsapi.aliyuncs.com"
        request.version = "2017-05-25"
        request.action = "SendSms"

        //手机号
        request.putQueryParameter("PhoneNumbers", phone)
        //签名名称
        request.putQueryParameter("SignName", "我的谷粒在线教育网站")
        //模板code
        request.putQueryParameter("TemplateCode", "SMS_180051135")
        //验证码  使用json格式   {"code":"123456"}
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param))
        try {
            val response = client.getCommonResponse(request)
            println(response.data)
            return response.httpResponse.isSuccess
        } catch (e: ServerException) {
            e.printStackTrace()
        } catch (e: ClientException) {
            e.printStackTrace()
        }

//        return false;
        return true //for test.
    }
}