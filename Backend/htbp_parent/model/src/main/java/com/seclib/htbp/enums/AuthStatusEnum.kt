package com.seclib.htbp.enums

import com.seclib.htbp.enums.AuthStatusEnum
import com.seclib.htbp.enums.OrderStatusEnum
import java.util.HashMap

enum class AuthStatusEnum( var status: Int, var itemName: String) {

    NO_AUTH(0, "未认证"), AUTH_RUN(1, "认证中"), AUTH_SUCCESS(2, "认证成功"), AUTH_FAIL(-1, "认证失败");


    companion object {
        @JvmStatic
        fun getStatusNameByStatus(status: Int): String {
            val arrObj = values()
            for (obj in arrObj) {
                if (status == obj.status) {
                    return obj.itemName
                }
            }
            return ""
        }
    }
}