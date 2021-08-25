package com.seclib.htbp.order.controller

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.seclib.htbp.common.result.Result
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.order.service.OrderService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.PathVariable
import com.seclib.htbp.vo.order.OrderQueryVo
import com.seclib.htbp.enums.OrderStatusEnum
import com.seclib.htbp.model.order.OrderInfo

@Api(tags = ["订单接口"])
@RestController
@RequestMapping("/admin/order/orderInfo")
class OrderController {
    @Autowired
    private val orderService: OrderService? = null
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    fun index(
        @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable page: Long?,
        @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable limit: Long?,
        @ApiParam(name = "orderCountQueryVo", value = "查询对象", required = false) orderQueryVo: OrderQueryVo?
    ): Result<*> {
        val pageParam = Page<OrderInfo?>(
            page!!, limit!!
        )
        val pageModel = orderService!!.selectPage(pageParam, orderQueryVo)
        return Result.ok(pageModel)
    }

    @get:GetMapping("getStatusList")
    @get:ApiOperation(value = "获取订单状态")
    val statusList: Result<*>
        get() = Result.ok(OrderStatusEnum.statusList)

    @ApiOperation(value = "获取订单")
    @GetMapping("show/{id}")
    operator fun get(
        @ApiParam(name = "orderId", value = "订单id", required = true) @PathVariable id: Long
    ): Result<*> {
        return Result.ok(
            orderService!!.show(id)
        )
    }
}