package com.seclib.htbp.order.service.impl

import com.alibaba.fastjson.JSONObject
import com.seclib.htbp.order.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.order.service.WeChatService
import com.seclib.htbp.order.service.PaymentService
import com.seclib.htbp.enums.PaymentTypeEnum
import com.seclib.htbp.order.service.RefundInfoService
import com.seclib.htbp.enums.RefundStatusEnum
import org.springframework.data.redis.core.RedisTemplate
import com.github.wxpay.sdk.WXPayUtil
import java.lang.Exception
import com.github.wxpay.sdk.WXPayConstants
import com.seclib.htbp.order.utils.ConstantPropertiesUtils
import com.seclib.htbp.order.utils.HttpClient
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

@Service
open class WeChatServiceImpl : WeChatService {
    @Autowired
    private val orderService: OrderService? = null

    @Autowired
    private val paymentService: PaymentService? = null

    @Autowired
    private val redisTemplate: RedisTemplate<*, *>? = null

    @Autowired
    private val refundInfoService: RefundInfoService? = null

    /**
     * 根据订单号下单，生成支付链接
     */
    override fun createNative(orderId: Long): Map<String,String> {
        return try {
            val payMap = redisTemplate!!.opsForValue()[orderId.toString()]
            if (null != payMap) return payMap as  Map<String,String>
            //根据id获取订单信息
            val order = orderService!!.getById(orderId)
            if (null === order) return HashMap<String,String>()
            // 保存交易记录
            paymentService!!.savePaymentInfo(order, PaymentTypeEnum.WEIXIN.status)
            //1、设置参数
            val paramMap = mutableMapOf<String,String>()
            paramMap["appid"] = ConstantPropertiesUtils.APPID
            paramMap["mch_id"] = ConstantPropertiesUtils.PARTNER
            paramMap["nonce_str"] = WXPayUtil.generateNonceStr()
            val body = order.reserveDate.toString() + "就诊" + order.depname
            paramMap["body"] = body
            paramMap["out_trade_no"] = order.outTradeNo!!
            //paramMap.put("total_fee", order.getAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap["total_fee"] = "1"
            paramMap["spbill_create_ip"] = "127.0.0.1"
            paramMap["notify_url"] = "http://guli.shop/api/order/weixinPay/weixinNotify"
            paramMap["trade_type"] = "NATIVE"
            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            val client = HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder")
            //client设置参数
            client.xmlParam = WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY)
            client.isHttps = true
            client.post()
            //3、返回第三方的数据
            val xml = client.content
            val resultMap = WXPayUtil.xmlToMap(xml)
            println("resultMap:$resultMap")
            //4、封装返回结果集
            val map = mutableMapOf<String,String>()
            map["orderId"] = orderId.toString()
            map["totalFee"] = order.amount.toString()
            map["resultCode"] = resultMap["result_code"].toString()
            map["codeUrl"] = resultMap["code_url"].toString()
            if (null != resultMap["result_code"]) {
                //微信支付二维码2小时过期，可采取2小时未支付取消订单
                (redisTemplate as RedisTemplate<String,Any>).opsForValue().set( orderId.toString(), map , 120, TimeUnit.MINUTES)
            }
            map
        } catch (e: Exception) {
            e.printStackTrace()
            HashMap<String,String>()
        }
    }

    override fun queryPayStatus(orderId: Long): Map<String, String> {
        try {

            val paramMap= mutableMapOf<String,String>()
            val orderInfo = orderService!!.getById(orderId)
            if (null === orderInfo) return paramMap
            paramMap["appid"] = ConstantPropertiesUtils.APPID
            paramMap["mch_id"] = ConstantPropertiesUtils.PARTNER
            paramMap["out_trade_no"] = orderInfo.outTradeNo!!
            paramMap["nonce_str"] = WXPayUtil.generateNonceStr()
            //2、设置请求
            val client = HttpClient("https://api.mch.weixin.qq.com/pay/orderquery")
            client.xmlParam = WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY)
            client.isHttps = true
            client.post()
            //3、返回第三方的数据，转成Map
            val xml = client.content
            val resultMap = WXPayUtil.xmlToMap(xml)
            println("Payment result:$resultMap")
            //4、返回
            return resultMap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return HashMap<String,String>()
    }

    override fun refund(orderId: Long): Boolean {
        try {
            val paymentInfoQuery = paymentService!!.getPaymentInfo(orderId, PaymentTypeEnum.WEIXIN.status)
            val refundInfo = refundInfoService!!.saveRefundInfo(paymentInfoQuery)
            if (refundInfo!!.refundStatus == RefundStatusEnum.REFUND.status) {
                return true
            }
            val paramMap = mutableMapOf<String,String>()
            paramMap["appid"] = ConstantPropertiesUtils.APPID //公众账号ID
            paramMap["mch_id"] = ConstantPropertiesUtils.PARTNER //商户编号
            paramMap["nonce_str"] = WXPayUtil.generateNonceStr()
            if (paymentInfoQuery!!.tradeNo == null) { //no valid account?
                paramMap["transaction_id"] = "1" //only for test.
            } else {
                paramMap["transaction_id"] = paymentInfoQuery.tradeNo.toString()
            }
            paramMap["out_trade_no"] = paymentInfoQuery.outTradeNo.toString()
            paramMap["out_refund_no"] = "tk" + paymentInfoQuery.outTradeNo //商户退款单号
            //       paramMap.put("total_fee",paymentInfoQuery.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
//       paramMap.put("refund_fee",paymentInfoQuery.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap["total_fee"] = "1"
            paramMap["refund_fee"] = "1"
            val paramXml = WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY)
            val client = HttpClient("https://api.mch.weixin.qq.com/secapi/pay/refund")
            client.xmlParam = paramXml
            client.isHttps = true
            client.isCert = true
            client.certPassword = ConstantPropertiesUtils.PARTNER
            client.post()
            //3、返回第三方的数据
            val xml = client.content
            val resultMap = WXPayUtil.xmlToMap(xml)

            //for test ----------------->>
            if (!WXPayConstants.SUCCESS.equals(resultMap!!["result_code"], ignoreCase = true)) {
                resultMap.remove("result_code")
                resultMap["result_code"] = "SUCCESS"
                resultMap["refund_id"] = "test_only"
            }
            //--------------------------<<
            if (null != resultMap && WXPayConstants.SUCCESS.equals(resultMap["result_code"], ignoreCase = true)) {
                refundInfo.callbackTime = Date()
                refundInfo.tradeNo = resultMap["refund_id"]
                refundInfo.refundStatus = RefundStatusEnum.REFUND.status
                refundInfo.callbackContent = JSONObject.toJSONString(resultMap)
                refundInfoService.updateById(refundInfo)
                return true
            }
            return false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}