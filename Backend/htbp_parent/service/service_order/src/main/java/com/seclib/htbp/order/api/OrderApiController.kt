package com.seclib.htbp.order.api

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.seclib.htbp.common.result.Result
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.order.service.OrderService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.GetMapping
import com.seclib.htbp.vo.order.OrderQueryVo
import javax.servlet.http.HttpServletRequest
import com.seclib.htbp.common.utils.AuthContextHolder
import com.seclib.htbp.enums.OrderStatusEnum
import com.seclib.htbp.model.order.OrderInfo
import org.springframework.web.bind.annotation.RequestBody
import com.seclib.htbp.vo.order.OrderCountQueryVo

@Api(tags = ["订单接口"])
@RestController
@RequestMapping("/api/order/orderInfo")
class OrderApiController {
    @Autowired
    private val orderService: OrderService? = null
    @ApiOperation(value = "创建订单")
    @PostMapping("auth/submitOrder/{scheduleId}/{patientId}")
    fun submitOrder(
        @ApiParam(name = "scheduleId", value = "排班id", required = true) @PathVariable scheduleId: String,
        @ApiParam(name = "patientId", value = "就诊人id", required = true) @PathVariable patientId: Long
    ): Result<*> {
        return Result.ok(orderService!!.saveOrder(scheduleId, patientId))
    }

    @GetMapping("auth/getOrders/{orderId}")
    fun getOrders(@PathVariable orderId: String?): Result<*> {
        val orderInfo = orderService!!.getOrder(orderId)
        return Result.ok(orderInfo)
    }

    //订单列表（条件查询带分页）
    @GetMapping("auth/{page}/{limit}")
    fun list(
        @PathVariable page: Long,
        @PathVariable limit: Long,
        orderQueryVo: OrderQueryVo, request: HttpServletRequest?
    ): Result<*> {
        //设置当前用户id
        orderQueryVo.userId = AuthContextHolder.getUserId(request)
        val pageParam = Page<OrderInfo?>(
            page!!, limit!!
        )
        val pageModel = orderService!!.selectPage(pageParam, orderQueryVo)
        return Result.ok(pageModel)
    }

    @get:GetMapping("auth/getStatusList")
    @get:ApiOperation(value = "获取订单状态")
    val statusList: Result<*>
        get() = Result.ok(OrderStatusEnum.getStatusList())

    @GetMapping("auth/cancelOrder/{orderId}")
    fun cancelOrder(@PathVariable orderId: Long): Result<*> {
        val isOrder = orderService!!.cancelOrder(orderId)
        return Result.ok(isOrder)
    }

    @ApiOperation(value = "获取订单统计数据")
    @PostMapping("inner/getCountMap")
    fun getCountMap(@RequestBody orderCountQueryVo: OrderCountQueryVo?): Map<String, Any>? {
        return orderService!!.getCountMap(orderCountQueryVo!!)
    }
}