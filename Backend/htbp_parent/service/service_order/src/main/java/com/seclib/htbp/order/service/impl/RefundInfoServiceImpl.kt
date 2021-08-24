package com.seclib.htbp.order.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.seclib.htbp.model.order.PaymentInfo
import com.seclib.htbp.order.mapper.RefundInfoMapper
import com.seclib.htbp.model.order.RefundInfo
import com.seclib.htbp.order.service.RefundInfoService
import com.seclib.htbp.enums.RefundStatusEnum
import org.springframework.stereotype.Service
import java.util.*

@Service
open class RefundInfoServiceImpl : ServiceImpl<RefundInfoMapper?, RefundInfo?>(), RefundInfoService {
    override fun saveRefundInfo(paymentInfo: PaymentInfo?): RefundInfo {
        if(paymentInfo == null) {
            return RefundInfo()
        }
        val queryWrapper = QueryWrapper<RefundInfo>()
        queryWrapper.eq("order_id", paymentInfo.orderId)
        queryWrapper.eq("payment_type", paymentInfo.paymentType)
        var refundInfo = baseMapper!!.selectOne(queryWrapper)
        if (null != refundInfo) return refundInfo
        // 保存交易记录
        refundInfo = RefundInfo()
        refundInfo.createTime = Date()
        refundInfo.orderId = paymentInfo.orderId
        refundInfo.paymentType = paymentInfo.paymentType
        refundInfo.outTradeNo = paymentInfo.outTradeNo
        refundInfo.refundStatus = RefundStatusEnum.UNREFUND.status
        refundInfo.subject = paymentInfo.subject
        //paymentInfo.setSubject("test");
        refundInfo.totalAmount = paymentInfo.totalAmount
        baseMapper!!.insert(refundInfo)
        return refundInfo
    }
}