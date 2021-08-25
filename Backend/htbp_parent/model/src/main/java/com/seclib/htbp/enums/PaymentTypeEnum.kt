package com.seclib.htbp.enums

import com.seclib.htbp.enums.AuthStatusEnum
import com.seclib.htbp.enums.OrderStatusEnum
import java.util.HashMap

enum class PaymentTypeEnum(var status: Int, var comment: String) {
    ALIPAY(1, "支付宝"), WEIXIN(2, "微信");

}