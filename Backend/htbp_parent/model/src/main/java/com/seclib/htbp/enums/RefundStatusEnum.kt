package com.seclib.htbp.enums

import com.seclib.htbp.enums.AuthStatusEnum
import com.seclib.htbp.enums.OrderStatusEnum
import java.util.HashMap

enum class RefundStatusEnum(var status: Int,  var itemName: String) {
    UNREFUND(1, "退款中"), REFUND(2, "已退款");

}