package com.seclib.htbp.order.api

import com.seclib.htbp.common.result.Result
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.GetMapping
import com.seclib.htbp.order.service.WeChatService
import com.seclib.htbp.order.service.PaymentService

@RestController
@RequestMapping("/api/order/wechat")
class WeChatApiController {
    @Autowired
    private val weChatService: WeChatService? = null

    @Autowired
    private val paymentService: PaymentService? = null

    /**
     * 下单 生成二维码
     */
    @GetMapping("createNative/{orderId}")
    fun createNative(
        @ApiParam(name = "orderId", value = "订单id", required = true) @PathVariable("orderId") orderId: Long
    ): Result<*> {
        return Result.ok(weChatService!!.createNative(orderId))
    }

    @GetMapping("queryPayStatus/{orderId}")
    fun queryPayStatus(@PathVariable orderId: Long): Result<*> {
        val resultMap = weChatService!!.queryPayStatus(orderId)
            ?: return Result.fail<Any>().message("Payment error")
        //if(("SUCCESS").equals(resultMap.get("trade_state")))
        run {
            val outTradeNo = resultMap["out_trade_no"] ?: ""
            paymentService!!.paySuccess(outTradeNo, resultMap)
            return Result.ok<Any>().message("Payment success")
        }
        //        return Result.ok().message("Paying");
    }
}