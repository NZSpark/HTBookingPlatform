package com.seclib.htbp.order.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.seclib.htbp.order.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.hosp.client.HospitalFeignClient
import com.seclib.htbp.common.exception.HtbpException
import com.seclib.htbp.enums.OrderStatusEnum
import com.seclib.htbp.order.mapper.PaymentInfoMapper
import com.seclib.htbp.model.order.PaymentInfo
import com.seclib.htbp.order.service.PaymentService
import com.seclib.htbp.enums.PaymentStatusEnum
import com.seclib.htbp.enums.PaymentTypeEnum
import com.seclib.htbp.common.helper.HttpRequestHelper
import com.seclib.htbp.common.result.ResultCodeEnum
import com.seclib.htbp.model.order.OrderInfo
import org.joda.time.DateTime
import org.springframework.stereotype.Service
import java.util.*

@Service
open class PaymentServiceImpl : ServiceImpl<PaymentInfoMapper?, PaymentInfo?>(), PaymentService {
    @Autowired
    private val orderService: OrderService? = null

    @Autowired
    private val hospitalFeignClient: HospitalFeignClient? = null

    /**
     * 保存交易记录
     * @param orderInfo
     * @param paymentType 支付类型（1：微信 2：支付宝）
     */
    override fun savePaymentInfo(orderInfo: OrderInfo?, paymentType: Int?) {
        if(orderInfo == null) return
        if(paymentType == null) return
        val queryWrapper = QueryWrapper<PaymentInfo>()
        queryWrapper.eq("order_id", orderInfo.id)
        queryWrapper.eq("payment_type", paymentType)
        val count = baseMapper!!.selectCount(queryWrapper)
        if (count > 0) return
        // 保存交易记录
        val paymentInfo = PaymentInfo()
        paymentInfo.createTime = Date()
        paymentInfo.orderId = orderInfo.id
        paymentInfo.paymentType = paymentType
        paymentInfo.outTradeNo = orderInfo.outTradeNo
        paymentInfo.paymentStatus = PaymentStatusEnum.UNPAID.status
        val subject =
            DateTime(orderInfo.reserveDate).toString("yyyy-MM-dd") + "|" + orderInfo.hosname + "|" + orderInfo.depname + "|" + orderInfo.title
        paymentInfo.subject = subject
        paymentInfo.totalAmount = orderInfo.amount
        baseMapper!!.insert(paymentInfo)
    }

    override fun paySuccess(out_trade_no: String, resultMap: Map<String, String>) {
        //1. query record of payment by out_trade_no
        val queryWrapper = QueryWrapper<PaymentInfo>()
        queryWrapper.eq("out_trade_no", out_trade_no)
        queryWrapper.eq("payment_type", PaymentTypeEnum.WEIXIN.status)
        val paymentInfo = baseMapper!!.selectOne(queryWrapper)
            ?: throw HtbpException(ResultCodeEnum.PARAM_ERROR)
        if (paymentInfo.paymentStatus !== PaymentStatusEnum.UNPAID.status) {
            return
        }

        //2. update payment record
        paymentInfo.paymentStatus = PaymentStatusEnum.PAID.status
        paymentInfo.tradeNo = resultMap?.get("transaction_id") ?: "1"
        paymentInfo.callbackTime = Date()
        paymentInfo.callbackContent = resultMap.toString()
        baseMapper!!.updateById(paymentInfo)

        //3. query order record by record id.
        val orderInfo = orderService!!.getById(paymentInfo.orderId) ?: return

        //4. update order record
        orderInfo.orderStatus = OrderStatusEnum.PAID.status
        orderService.updateById(orderInfo)

        //5. update hospital interface
        val signInfoVo = hospitalFeignClient!!.getSignInfoVo(orderInfo.hoscode)
        val reqMap = mutableMapOf<String,Any?>()
        reqMap["hoscode"] = orderInfo.hoscode
        reqMap["hosRecordId"] = orderInfo.hosRecordId
        reqMap["timestamp"] = HttpRequestHelper.timestamp
        val sign = HttpRequestHelper.getSign(reqMap, signInfoVo!!.signKey)
        reqMap["sign"] = sign
        val result = HttpRequestHelper.sendRequest(reqMap, signInfoVo.apiUrl + "/order/updatePayStatus")
        if (result.getInteger("code") != 200) {
            throw HtbpException(result.getString("message"), ResultCodeEnum.FAIL.code)
        }
    }

    override fun getPaymentInfo(orderId: Long?, paymentType: Int?): PaymentInfo? {
        val queryWrapper = QueryWrapper<PaymentInfo>()
        queryWrapper.eq("order_id", orderId)
        queryWrapper.eq("payment_type", paymentType)
        return baseMapper!!.selectOne(queryWrapper)
    }
}