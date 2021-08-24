package com.seclib.htbp.order.service

import com.baomidou.mybatisplus.extension.service.IService
import com.seclib.htbp.model.order.OrderInfo
import com.seclib.htbp.vo.order.OrderQueryVo
import com.seclib.htbp.vo.order.OrderCountQueryVo
import com.seclib.htbp.model.order.PaymentInfo
import com.seclib.htbp.model.order.RefundInfo

interface PaymentService : IService<PaymentInfo?> {
    /**
     * 保存交易记录
     * @param order
     * @param paymentType 支付类型（1：微信 2：支付宝）
     */
    fun savePaymentInfo(order: OrderInfo?, paymentType: Int?)
    fun paySuccess(out_trade_no: String, resultMap: Map<String, String>)

    /**
     * 获取支付记录
     * @param orderId
     * @param paymentType
     * @return
     */
    fun getPaymentInfo(orderId: Long?, paymentType: Int?): PaymentInfo?
}