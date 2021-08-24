package com.seclib.htbp.order.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import com.seclib.htbp.model.order.OrderInfo
import com.seclib.htbp.vo.order.OrderQueryVo
import com.seclib.htbp.vo.order.OrderCountQueryVo
import com.seclib.htbp.model.order.PaymentInfo
import com.seclib.htbp.model.order.RefundInfo

interface OrderService : IService<OrderInfo?> {
    //保存订单
    fun saveOrder(scheduleId: String, patientId: Long): Long?
    fun getOrder(orderId: String?): OrderInfo?
    fun selectPage(pageParam: Page<OrderInfo?>?, orderQueryVo: OrderQueryVo?): IPage<OrderInfo?>?

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    fun show(orderId: Long): Map<String, Any>

    /**
     * 取消订单
     * @param orderId
     */
    fun cancelOrder(orderId: Long): Boolean

    /**
     * 就诊提醒
     */
    fun patientTips()

    /**
     * 订单统计
     */
    fun getCountMap(orderCountQueryVo: OrderCountQueryVo): Map<String, Any>?
}