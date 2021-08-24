package com.seclib.htbp.order.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import com.seclib.htbp.vo.order.OrderCountQueryVo
import org.springframework.stereotype.Repository

@FeignClient(value = "service-order")
@Repository
interface OrderFeignClient {
    /**
     * 获取订单统计数据
     */
    @PostMapping("/api/order/orderInfo/inner/getCountMap")
    fun getCountMap(@RequestBody orderCountQueryVo: OrderCountQueryVo?): Map<String?, Any?>?
}