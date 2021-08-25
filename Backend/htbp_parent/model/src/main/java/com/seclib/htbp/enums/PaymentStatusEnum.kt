package com.seclib.htbp.enums

import com.seclib.htbp.enums.AuthStatusEnum
import com.seclib.htbp.enums.OrderStatusEnum
import java.util.HashMap

enum class PaymentStatusEnum(//REFUND(-1,"已退款");
    var status: Int,  var itemName: String
) {
    UNPAID(1, "支付中"), PAID(2, "已支付");

}