package com.seclib.htbp.order.service

import com.seclib.htbp.vo.order.OrderQueryVo
import com.seclib.htbp.vo.order.OrderCountQueryVo
import com.seclib.htbp.model.order.PaymentInfo
import com.seclib.htbp.model.order.RefundInfo

interface WeChatService {
    fun createNative(orderId: Long): Map<String,String>?
    fun queryPayStatus(orderId: Long): Map<String, String>?

    /***
     * 退款
     * @param orderId
     * @return
     */
    fun refund(orderId: Long): Boolean
}