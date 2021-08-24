package com.seclib.htbp.order.service

import com.baomidou.mybatisplus.extension.service.IService
import com.seclib.htbp.vo.order.OrderQueryVo
import com.seclib.htbp.vo.order.OrderCountQueryVo
import com.seclib.htbp.model.order.PaymentInfo
import com.seclib.htbp.model.order.RefundInfo

interface RefundInfoService : IService<RefundInfo?> {
    /**
     * 保存退款记录
     * @param paymentInfo
     */
    fun saveRefundInfo(paymentInfo: PaymentInfo?): RefundInfo?
}