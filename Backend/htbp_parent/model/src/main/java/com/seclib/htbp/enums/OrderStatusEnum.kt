package com.seclib.htbp.enums

import com.seclib.htbp.enums.AuthStatusEnum
import com.seclib.htbp.enums.OrderStatusEnum
import java.util.ArrayList
import java.util.HashMap

enum class OrderStatusEnum(var status: Int, var comment: String) {
    UNPAID(0, "预约成功，待支付"), PAID(1, "已支付"), GET_NUMBER(2, "已取号"), CANCLE(-1, "取消预约");

    companion object {
        fun getStatusNameByStatus(status: Int): String {
            val arrObj = values()
            for (obj in arrObj) {
                if (status == obj.status) {
                    return obj.comment
                }
            }
            return ""
        }

        val statusList: List<Map<String, Any>>
            get() {
                val list: MutableList<Map<String, Any>> = ArrayList()
                val arrObj = values()
                for (obj in arrObj) {
                    val map: MutableMap<String, Any> = HashMap()
                    map["status"] = obj.status
                    map["comment"] = obj.comment
                    list.add(map)
                }
                return list
            }
    }
}