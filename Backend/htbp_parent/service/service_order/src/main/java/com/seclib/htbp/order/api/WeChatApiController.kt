package com.seclib.htbp.order.api;

import com.seclib.htbp.common.result.Result;
import com.seclib.htbp.order.service.PaymentService;
import com.seclib.htbp.order.service.WeChatService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/order/wechat")
public class WeChatApiController {
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private PaymentService paymentService;
    /**
     * 下单 生成二维码
     */
    @GetMapping("createNative/{orderId}")
    public Result createNative(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @PathVariable("orderId") Long orderId) {
        return Result.ok(weChatService.createNative(orderId));
    }

    @GetMapping("queryPayStatus/{orderId}")
    public Result queryPayStatus(@PathVariable Long orderId){
        Map<String,String> resultMap = weChatService.queryPayStatus(orderId);
        if(resultMap == null){
            return Result.fail().message("Payment error");
        }
        //if(("SUCCESS").equals(resultMap.get("trade_state")))
        {
            String out_trade_no =  resultMap.get("out_trade_no");
            paymentService.paySuccess(out_trade_no,resultMap);
            return  Result.ok().message("Payment success");
        }
//        return Result.ok().message("Paying");
    }

}
