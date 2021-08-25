package com.seclib.htbp.statistics.controller

import com.seclib.htbp.common.result.Result
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import com.seclib.htbp.order.client.OrderFeignClient
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import io.swagger.annotations.ApiParam
import com.seclib.htbp.vo.order.OrderCountQueryVo

@Api(tags = ["统计管理接口"])
@RestController
@RequestMapping("/admin/statistics")
class StatisticsController {
    @Autowired
    private val orderFeignClient: OrderFeignClient? = null
    @ApiOperation(value = "获取订单统计数据")
    @GetMapping("getCountMap")
    fun getCountMap(
        @ApiParam(
            name = "orderCountQueryVo",
            value = "查询对象",
            required = false
        ) orderCountQueryVo: OrderCountQueryVo?
    ): Result<*> {
        return Result.ok(
            orderFeignClient!!.getCountMap(orderCountQueryVo)
        )
    }
}